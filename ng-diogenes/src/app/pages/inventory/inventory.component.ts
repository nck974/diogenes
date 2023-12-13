import { BreakpointObserver } from '@angular/cdk/layout';
import { Location } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, catchError, finalize } from 'rxjs';
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
  isLoading = true;
  fetchingInProgress = false;
  isLargeScreen = this.breakpointObserver.isMatched('(min-width: 768px)');

  constructor(
    private itemService: ItemService,
    private router: Router,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private location: Location,
    private breakpointObserver: BreakpointObserver
  ) { }

  ngOnInit(): void {
    this.setUrlParameters();
    this.fetchNextPage();
  }

  ngOnDestroy(): void {
    this.itemServiceSubscription?.unsubscribe();
  }

  setUrlParameters(): void {
    this.route.queryParams.subscribe(params => {
      this.itemFilter = {
        categoryId: params['categoryId'] || undefined,
        name: params['name'] || undefined,
        description: params['description'] || undefined,
        number: params['number'] || undefined
      };

      // Set to undefined if everything is not defined
      if (Object.values(this.itemFilter).every(value => value === undefined)) {
        this.itemFilter = undefined;
      }

      this.itemSorter = {
        direction: params["direction"] || this.itemSorter.direction,
        field: params["field"] || this.itemSorter.field,
      }

    });
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
    this.itemServiceSubscription = this.itemService.getItems(
      nextPage,
      this.itemFilter,
      this.itemSorter,
      this.isLargeScreen ? 50 : 20)
      .pipe(
        catchError((error) => {
          console.error('Error fetching next page:', error);
          return [];
        }),
        finalize(() => this.isLoading = false)
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
    // Note: Is loading is set to false after the subscription of fetching the next page
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

  private updateUrlParameters(): void {
    let url = "/items?";

    for (let urlParameters of [this.itemFilter, this.itemSorter]) {
      if (urlParameters) {
        const filterQueryString = Object.entries(urlParameters)
          .filter(([_key, value]) => value !== undefined && value != null)
          .map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`)
          .join('&');
        url = `${url}&${filterQueryString}`;
      }
    }

    this.location.replaceState(url);
  }

  private onFilterItems(filter?: ItemFilter | null): void {
    if (JSON.stringify(filter) === JSON.stringify(this.itemFilter)) {
      return;
    }

    // Reset filter (different from closing the popup with undefined)
    if (filter != null) {
      this.itemFilter = filter;
    } else {
      this.itemFilter = undefined;
    }

    this.resetLoadedItems()
    this.updateUrlParameters();
  }

  private onSortItems(sorter: ItemSorter) {
    if (JSON.stringify(sorter) === JSON.stringify(this.itemSorter)) {
      return;
    }

    this.itemSorter = sorter;

    this.resetLoadedItems()
    this.updateUrlParameters();
  }

  onCreateNewItem() {
    this.router.navigateByUrl("/items/edit/new")
  }

  isItemsListNotEmpty(): boolean {
    if (this.items && this.items.length > 0) {
      return true;
    }
    return false;
  }

}
