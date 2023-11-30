import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Subscription, catchError } from 'rxjs';
import { Item } from 'src/app/models/Item';
import { ItemFilter } from 'src/app/models/ItemFilter';
import { ItemSorter } from 'src/app/models/ItemSorter';
import { ItemService } from 'src/app/shared/services/item.service';
import { InventoryFilterComponent } from './components/inventory-filter/inventory-filter.component';
import { InventorySorterComponent } from './components/inventory-sorter/inventory-sorter.component';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss']
})
export class InventoryComponent implements OnInit, OnDestroy {

  private itemServiceSubscription?: Subscription;

  private currentPage: number = 0;
  lastPage: boolean = false
  private itemFilter?: ItemFilter;
  private itemSorter: ItemSorter = { field: "ID", direction: "DESC" };

  items: Item[] = [];
  isLoading = false;
  fetchingInProgress = false;

  constructor(private itemService: ItemService, private router: Router, private dialog: MatDialog,) { }

  ngOnInit(): void {
    this.fetchNextPage()
  }

  ngOnDestroy(): void {
    this.itemServiceSubscription?.unsubscribe();
  }

  onScroll() {
    if (!this.fetchingInProgress && !this.lastPage) {
      this.fetchingInProgress = true;
      this.fetchNextPage();
    }
  }

  fetchNextPage(): void {
    if (this.lastPage) {
      return;
    }

    const nextPage = this.currentPage;
    this.itemServiceSubscription = this.itemService.getItems(nextPage, this.itemFilter, this.itemSorter)
      .pipe(
        catchError((error) => {
          console.error('Error fetching next page:', error);
          return [];
        }),
      )
      .subscribe((page) => {
        this.currentPage = page.number + 1;
        this.lastPage = page.last;
        this.items.push(...page.content);
        this.fetchingInProgress = false;
      });
  }

  resetLoadedItems() {
    this.isLoading = true;
    this.currentPage = 0;
    this.lastPage = false;
    this.items.splice(0, this.items.length);
    this.fetchNextPage();
    this.isLoading = false;
  }

  onOpenFilter(): void {
    const dialogRef = this.dialog.open(InventoryFilterComponent, {
      data: this.itemFilter,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === undefined) {
        return;
      }
      this.onFilterItems(result);
    });
  }

  onOpenSorter(): void {
    const dialogRef = this.dialog.open(InventorySorterComponent, {
      data: this.itemSorter,
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === undefined) {
        return;
      }
      this.onSortItems(result);
    });
  }

  private onFilterItems(filter?: ItemFilter | null): void {
    if (JSON.stringify(filter) === JSON.stringify(this.itemFilter)) {
      return;
    }

    // Reset filter (different from closing the popup with undefined)
    if (filter === null) {
      filter = undefined;
    }

    this.itemFilter = filter;
    this.resetLoadedItems()
  }

  private onSortItems(sorter: ItemSorter) {
    if (JSON.stringify(sorter) === JSON.stringify(this.itemSorter)) {
      return;
    }

    this.itemSorter = sorter;
    this.resetLoadedItems()
  }

  onCreateNewItem() {
    this.router.navigateByUrl("/items/edit/new")
  }

}
