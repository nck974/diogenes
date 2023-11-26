import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Observable, Subscription, catchError, combineLatest, finalize, map, of, switchMap, take, throwError } from 'rxjs';
import { Category } from 'src/app/models/Category';
import { ImageTransfer } from 'src/app/models/ImageTransfer';
import { Item } from 'src/app/models/Item';
import { CategoryService } from 'src/app/shared/services/category.service';
import { ItemService } from 'src/app/shared/services/item.service';
import { MessageService } from 'src/app/shared/services/message.service';

@Component({
  selector: 'app-edit-single-item',
  templateUrl: './edit-single-item.component.html',
  styleUrls: ['./edit-single-item.component.scss']
})
export class EditSingleItemComponent implements OnDestroy, OnInit {
  @Input() createFromImage?: ImageTransfer;
  @Output() onItemCreate = new EventEmitter<boolean>();
  initializationSubscription?: Subscription;

  itemForm: FormGroup;
  categories?: Category[];
  item?: Item;
  initializationError?: string;
  isNewItem: boolean = true;
  isLoading: boolean = true;

  imagePath?: string;
  selectedImageBlob?: Blob;

  constructor(
    private fb: FormBuilder,
    private itemService: ItemService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private messageService: MessageService,
    public dialogService: MatDialog) {
    this.itemForm = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.maxLength(2000)]],
      number: [1, [Validators.required, Validators.pattern(/^\d+$/)]],
      categoryId: [null, Validators.required]
    });
  }

  ngOnDestroy(): void {
    this.initializationSubscription?.unsubscribe();
  }

  ngOnInit(): void {
    combineLatest({
      categories: this.categoryService.getCategories(),
      item: this.getItemToEdit(),
    })
      .pipe(finalize(
        () => {
          this.isLoading = false;
        }
      ))
      .subscribe(({ categories, item }) => {
        this.categories = categories;
        if (item) {
          this.isNewItem = false;
          this.item = item;
          this.initializeEditFormWithItemData(item);
        } else if (this.createFromImage) {
          this.selectedImageBlob = this.createFromImage.file;
        }
        this.preselectPreviousCategory();
      });
  }

  /// If there is ca category saved in the browser and it is a new item auto-select the category
  /// as it is likely to be creating items from a similar category
  private preselectPreviousCategory(): void {

    if (!this.isNewItem) {
      return;
    }

    let lastCategory = localStorage.getItem("last-category");
    if (lastCategory && this.categories) {
      for (let category of this.categories) {
        if (category.name == lastCategory) {
          this.itemForm.get("categoryId")?.setValue(category.id);
          break;
        }
      }
    }
  }

  onAddNewImage(newValue?: Blob) {
    this.selectedImageBlob = newValue;
  }

  private getItemToEdit(): Observable<Item | undefined> {
    const firstChild = this.route.firstChild;
    if (!firstChild) {
      return of(undefined);
    }
    return firstChild.paramMap.pipe(
      take(1), // paramMap keeps emitting values
      map(params => this.getRouteParameters(params)),
      switchMap(itemId => {
        if (itemId) {
          return this.itemService.getItem(itemId)
        }
        return throwError(() => new Error("Item ID not provided"));
      }
      ),
      catchError(error => {
        if (error.message !== "Item ID not provided") {
          this.initializationError = "The item to edit could not be found.";
          this.isNewItem = true;
        }
        return of(undefined);
      })
    );
  }

  private getRouteParameters(params: ParamMap): number | undefined {
    console.log(params);

    const idString = params.get("id");
    if (idString) {
      const id = parseInt(idString);
      if (isNaN(id)) {
        this.initializationError = `Error: Invalid id  ${idString} received to edit the item`;
        throwError(() => new Error(this.initializationError));
      }
      return id;
    }

    return undefined;
  }

  private initializeEditFormWithItemData(item: Item): void {

    if (item.imagePath) {
      this.imagePath = item.imagePath;
    }

    this.itemForm.patchValue({
      name: item.name,
      description: item.description,
      number: item.number,
      categoryId: item.category.id,
    })
  }

  onSubmit(): void {
    if (this.itemForm.valid) {

      this.isLoading = true;
      const newItem: Item = this.itemForm.value as Item;
      console.log(newItem);

      // Select service
      let manageItemObservable = this.itemService.postItem(newItem, this.selectedImageBlob);
      let itemTypeMessage = "created";
      if (!this.isNewItem) {
        newItem.id = this.item?.id!;
        manageItemObservable = this.itemService.updateItem(newItem, this.selectedImageBlob);
        itemTypeMessage = "updated";
      }

      manageItemObservable
        .pipe(
          catchError(error => {
            this.messageService.add(`Item ${newItem.name} could not be ${itemTypeMessage}. ${error}`)
            return throwError(() => error);
          }),
          finalize(() => this.isLoading = false)
        )
        .subscribe(
          item => {
            this.messageService.add(`Item ${item.name} was ${itemTypeMessage}`);

            // Save the current category for the next item creation
            localStorage.setItem("last-category", item.category.name);

            this.onItemCreate.emit(true);
          }
        );
    }
  }

}
