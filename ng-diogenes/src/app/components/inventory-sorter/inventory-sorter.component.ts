import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ItemSorter } from 'src/app/models/ItemSorter';
import { SortingField } from 'src/app/models/SortingField';

@Component({
  selector: 'app-inventory-sorter',
  templateUrl: './inventory-sorter.component.html',
  styleUrls: ['./inventory-sorter.component.scss']
})
export class InventorySorterComponent implements OnInit {
  @Output() sortItems = new EventEmitter<ItemSorter>();
  options: string[] = []
  selectedOption: string = "Order by...";
  isOpen: boolean = false;
  optionHasBeenSelected: boolean = false;


  ngOnInit(): void {
    this.options = this.generateSortingOptions();
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  generateSortingOptions(): string[] {
    let sortingFields = Object.values(SortingField);

    // Generate ascending and descending for each field
    let options: string[] = [];
    sortingFields.forEach(
      (option) => {
        options.push(`${option}__ASC`);
        options.push(`${option}__DESC`);
      }
    )
    return options;
  }

  onSelectOption(option: string): void {
    this.optionHasBeenSelected = true;
    this.selectedOption = option;
    this.isOpen = false;
    const [filterName, filterDirection] = option.split('__');
    this.sortItems.emit({ field: filterName, direction: filterDirection });
  }

  private getOptionDetails(option: string): { filterName: string, isAscending: boolean } {
    const [filterName, filterDirection] = option.split('__');
    const isAscending = filterDirection === 'ASC';
    return { filterName, isAscending };
  }

  isSortAscending(option: string): boolean {
    const { isAscending } = this.getOptionDetails(option)
    if (isAscending) {
      return true;
    }
    return false;
  }

  displayOption(option: string): string {
    const { filterName } = this.getOptionDetails(option)
    return filterName;
  }

}
