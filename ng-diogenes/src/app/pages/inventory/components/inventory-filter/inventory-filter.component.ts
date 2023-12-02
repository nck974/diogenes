import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Category } from 'src/app/models/Category';
import { ItemFilter } from 'src/app/models/ItemFilter';
import { CategoryService } from 'src/app/shared/services/category.service';
import { isNumberValidator } from 'src/app/utils/form-validator/number';



@Component({
  selector: 'app-inventory-filter',
  templateUrl: './inventory-filter.component.html',
  styleUrls: ['./inventory-filter.component.scss']
})
export class InventoryFilterComponent implements OnInit, OnDestroy {

  form: FormGroup;

  // Fields
  nameFilterName: string = 'name';
  descriptionFilterName: string = 'description';
  numberFilterName: string = 'number';
  categoryIdFilterName: string = 'categoryId';

  // Dropbox options
  categorySubscription?: Subscription;
  categories: Category[] = [];

  // Status to clear filters
  filterIsActive = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    public dialogRef: MatDialogRef<InventoryFilterComponent>,
    @Inject(MAT_DIALOG_DATA) public previousFilter?: ItemFilter) {
    this.form = this.fb.group({
      name: new FormControl("", [Validators.maxLength(50)]),
      number: new FormControl("", [isNumberValidator()]),
      description: new FormControl("", [Validators.maxLength(200)]),
      categoryId: new FormControl("")
    });
  }

  ngOnDestroy(): void {
    this.categorySubscription?.unsubscribe();
  }

  ngOnInit(): void {
    this.categorySubscription = this.categoryService.getCategories().subscribe(categories => {
      this.categories = categories;
      this.prefillPreviousFilter();
    });
  }

  private prefillPreviousFilter(): void {
    if (this.previousFilter) {
      
      // If there no keys different to null then skip this
      const hasNonNullValue = Object.values(this.previousFilter).some(value => value !== null);
      if (!hasNonNullValue) {
        return;
      }

      this.form.patchValue(
        {
          "name": this.previousFilter.name,
          "number": this.previousFilter.number,
          "description": this.previousFilter.description,
          "categoryId": this.previousFilter.categoryId,
        }
      );
      this.filterIsActive = true;
    }
  }

  getFilterControl(filterName: string): FormControl {
    return this.form.get(filterName) as FormControl;
  }

  onApplyFilters() {
    if (!this.form.valid) {
      return;
    }

    const filter: ItemFilter = { ...this.form.value };
    this.dialogRef.close(filter);
  }

  onClearFilters() {
    this.form?.reset();
    this.dialogRef.close(null);
  }

}
