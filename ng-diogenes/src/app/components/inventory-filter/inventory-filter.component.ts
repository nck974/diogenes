import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Category } from 'src/app/models/Category';
import { ItemFilter } from 'src/app/models/ItemFilter';
import { CategoryService } from 'src/app/services/category.service';

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
  @Output() filterItems = new EventEmitter<ItemFilter>();

  form: FormGroup;

  nameFilterName: string = 'name';
  descriptionFilterName: string = 'description';
  numberFilterName: string = 'number';
  categoryIdFilterName: string = 'categoryId';

  categorySubscription?: Subscription;
  categories: Category[] = [];
  filterIsActive = false;

  constructor(private fb: FormBuilder, private categoryService: CategoryService) {
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
    this.categorySubscription = this.categoryService.getCategories().subscribe(categories => this.categories = categories);
  }

  getFilterControl(filterName: string): FormControl {
    return this.form.get(filterName) as FormControl;
  }

  onApplyFilters() {
    if (!this.form.valid) {
      return;
    }

    const filter: ItemFilter = { ...this.form.value };
    console.log("Filter is: ");
    console.log(filter);
    this.filterItems.emit(filter);
    this.filterIsActive = true;
  }

  onClearFilters() {
    this.form?.reset();
    this.filterItems.emit(undefined);
    this.filterIsActive = false;
  }

}
