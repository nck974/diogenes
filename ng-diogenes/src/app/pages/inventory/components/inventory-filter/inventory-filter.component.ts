import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Category } from 'src/app/models/Category';
import { ItemFilter } from 'src/app/models/ItemFilter';
import { CategoryService } from 'src/app/shared/services/category.service';

export function isNumberValidator(): ValidatorFn {

  console.log("Calling isNumberValidator")
  return (control: AbstractControl): ValidationErrors | null => {
    const isNumber = !isNaN(control.value as number);
    console.log("Is number is " + isNumber + " from " + control.value)
    return isNumber ? null : { isNotANumber: { value: control.value } };
  };
}

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
    @Inject(MAT_DIALOG_DATA) public previousFilter: ItemFilter) {
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

  private prefillPreviousFilter() {
    if (this.previousFilter) {
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
