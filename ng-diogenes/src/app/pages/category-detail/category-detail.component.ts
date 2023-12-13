import { Location, } from '@angular/common';
import { Component, OnDestroy, OnInit, } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription, catchError, finalize, of } from 'rxjs';
import { Category } from 'src/app/models/Category';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { CategoryService } from 'src/app/shared/services/category.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { getColorOrDefault } from 'src/app/utils/color';

@Component({
  selector: 'app-category-detail',
  templateUrl: './category-detail.component.html',
  styleUrls: ['./category-detail.component.scss']
})
export class CategoryDetailComponent implements OnInit, OnDestroy {

  categoryServiceSubscription?: Subscription;
  paramsSubscription?: Subscription;
  category?: Category;
  isLoading = false;
  constructor(
    private categoryService: CategoryService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private location: Location,
    public dialogService: MatDialog,
    private router: Router) {

  }

  ngOnInit(): void {
    this.getCategory();
  }

  ngOnDestroy(): void {
    this.categoryServiceSubscription?.unsubscribe();
    this.paramsSubscription?.unsubscribe();
  }

  private getCategory(): void {
    this.isLoading = true;
    this.paramsSubscription = this.route.paramMap
      .pipe(
        finalize(() => this.isLoading = false),
      )
      .subscribe(param => {
        const idParam = param.get("id");
        if (idParam) {
          const id = parseInt(idParam);
          this.queryItem(id)
        }
      });
  }

  private queryItem(id: number): void {
    this.categoryServiceSubscription = this.categoryService.getCategory(id)
      .pipe(
        finalize(() => this.isLoading = false),
      )
      .subscribe(category => {
        this.category = category;
      })
  }

  private openDialog(): Observable<any> {
    const dialogRef = this.dialogService.open(ConfirmationDialogComponent, {
      data: { title: "Delete category?", content: `Do you really want to delete ${this.category!.name}?` },
    });

    return dialogRef.afterClosed();
  }

  onNavigateBack(): void {
    this.location.back();
  }

  onEditCategory(): void {
    this.router.navigateByUrl(`/categories/${this.category?.id}/edit`);
  }

  private deleteCategory() {
    this.isLoading = true;
    this.categoryService.deleteCategory(this.category!.id)
      .pipe(
        finalize(() => this.isLoading = false),
        catchError((err) => {
          const errorMessage = err["error"]["message"];
          this.messageService.add(`The category could not be deleted. ${errorMessage}`);
          return of(1);
        })
      )
      .subscribe((result) => {
        if (result != 1) {
          this.messageService.add(`Category ${this.category!.name} was deleted`);

          this.onNavigateBack();
        }
      });
  }

  onDeleteCategory(): void {
    if (!this.category) {
      console.error("Trying to delete an category that is not defined");
    }

    this.openDialog().subscribe(result => {
      console.log('The dialog was closed: ' + result);
      if (result as boolean) {
        this.deleteCategory();
      }
    });

  }

  getAvatarColor(): string {
    let color = "";
    if (this.category) {
      color = getColorOrDefault(this.category.color);
    } else {
      color = getColorOrDefault();
    }
    return color;
  }
}
