import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ItemSorter } from 'src/app/models/ItemSorter';
import { SortingField } from 'src/app/models/SortingField';

@Component({
    selector: 'app-inventory-sorter',
    templateUrl: './inventory-sorter.component.html',
    styleUrls: ['./inventory-sorter.component.scss'],
    standalone: false
})
export class InventorySorterComponent implements OnInit {
  options: string[] = []
  selectedOption: string = "Sort by...";

  constructor(public dialogRef: MatDialogRef<InventorySorterComponent>,
    @Inject(MAT_DIALOG_DATA) public previousSorter: ItemSorter
  ) { }

  ngOnInit(): void {
    this.options = this.generateSortingOptions();
    this.preselectPreviousSorter();
  }

  private preselectPreviousSorter() {
    if (this.previousSorter) {
      this.selectedOption = `${this.previousSorter.field}__${this.previousSorter.direction}`;
    }
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
    this.selectedOption = option;
    const [filterName, filterDirection] = option.split('__');
    let itemSorter: ItemSorter = { field: filterName, direction: filterDirection };
    this.dialogRef.close(itemSorter);
  }

  private getOptionDetails(option: string): { filterName: string, isAscending: boolean } {
    const [filterName, filterDirection] = option.split('__');
    const isAscending = filterDirection === 'ASC';
    return { filterName, isAscending };
  }

  displayOption(option: string): string {
    const { filterName, isAscending } = this.getOptionDetails(option)

    if (isAscending) {
      return filterName + " ↑";
    }

    return filterName + " ↓";
  }

}
