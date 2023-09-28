import { Component, ViewChild } from '@angular/core';
import { Location } from '@angular/common';
import { Observable, Subscription, catchError, finalize, map, of, switchMap, take, throwError } from 'rxjs';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Category } from 'src/app/models/Category';
import { CategoryService } from 'src/app/shared/services/category.service';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { MessageService } from 'src/app/shared/services/message.service';
import { Color, NgxMatColorPickerInput } from '@angular-material-components/color-picker';
import { hexToRgb } from 'src/app/utils/color';

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.scss']
})
export class EditCategoryComponent {
  @ViewChild(NgxMatColorPickerInput) pickerInput?: NgxMatColorPickerInput;

  initializationSubscription?: Subscription;

  categoryForm: FormGroup;
  categories?: Category[];
  category?: Category;
  initializationError?: string;
  isNewCategory: boolean = true;
  isLoading: boolean = true;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
    private location: Location) {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.maxLength(2000)]],
      color: ['', [Validators.required]], // TODO: Get correct regex
    });

  }
  ngOnDestroy(): void {
    this.initializationSubscription?.unsubscribe();
  }

  ngOnInit(): void {
    this.initializationSubscription = this.getCategoryToEdit()
      .pipe(finalize(
        () => {
          console.log("Finalize");
          this.isLoading = false;
        }
      ))
      .subscribe((category: Category | undefined) => {
        if (category) {
          this.isNewCategory = false;
          this.category = category;
          this.initializeEditFormWithCategoryData(category);
        }
      });
  }

  // Create a getter for the color FormControl
  get colorControl(): FormControl {
    return this.categoryForm.get('color') as FormControl;
  }

  private getCategoryToEdit(): Observable<Category | undefined> {
    return this.route.paramMap.pipe(
      take(1), // paramMap keeps emitting values
      map(params => this.getRouteParameters(params)),
      switchMap(categoryId => {
        if (categoryId) {
          return this.categoryService.getCategory(categoryId)
        }
        return throwError(() => new Error("Category ID not provided"));
      }
      ),
      catchError(error => {
        if (error.message !== "Category ID not provided") {
          this.initializationError = "The category to edit could not be found.";
          this.isNewCategory = true;
        }
        return of(undefined);
      })
    );
  }

  private getRouteParameters(params: ParamMap): number | undefined {
    const idString = params.get("id");
    if (idString) {
      const id = parseInt(idString);
      if (isNaN(id)) {
        this.initializationError = `Error: Invalid id  ${idString} received to edit the category`;
        throwError(() => new Error(this.initializationError));
      }
      return id;
    }

    return undefined;
  }


  private initializeEditFormWithCategoryData(category: Category): void {
    this.categoryForm.patchValue({
      name: category.name,
      description: category.description,
    });
    const rgb = hexToRgb(category.color);
    if (rgb){
      this.categoryForm.patchValue({
        color: new Color(rgb.r, rgb.g, rgb.b)
      })
    }
  }

  onSubmit(): void {
    if (this.categoryForm.valid) {

      this.isLoading = true;
      const colorHex = (this.categoryForm.value["color"] as Color).hex
      const newCategory: Category = this.categoryForm.value as Category;
      newCategory.color = colorHex;
      console.log(newCategory);
      // Select service
      let manageCategoryObservable = this.categoryService.postCategory(newCategory);
      let categoryTypeMessage = "created";
      if (!this.isNewCategory) {
        newCategory.id = this.category?.id!;
        manageCategoryObservable = this.categoryService.updateCategory(newCategory);
        categoryTypeMessage = "updated";
      }

      manageCategoryObservable
        .pipe(
          catchError(error => {
            this.messageService.add(`Category ${newCategory.name} could not be ${categoryTypeMessage}. ${error}`)
            return throwError(() => error);
          }),
          finalize(() => this.isLoading = false)
        )
        .subscribe(
          category => {
            this.messageService.add(`Category ${category.name} was ${categoryTypeMessage}`);
            this.router.navigateByUrl("/categories");
          }
        );
    }
  }

  onNavigateBack(): void {
    this.location.back();
  }
}
