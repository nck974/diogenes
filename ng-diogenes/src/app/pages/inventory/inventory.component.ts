import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription, catchError, finalize } from 'rxjs';
import { Item } from 'src/app/models/Item';
import { ItemFilter } from 'src/app/models/ItemFilter';
import { ItemSorter } from 'src/app/models/ItemSorter';
import { ItemService } from 'src/app/services/item.service';
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss']
})
export class InventoryComponent implements OnInit, OnDestroy {

  private itemServiceSubscription?: Subscription;

  private currentPage: number = 0;
  private lastPage: boolean = false
  private itemFilter?: ItemFilter;
  private itemSorter: ItemSorter = { field: "ID", direction: "ASC" };

  items: Item[] = [];
  isLoading = false;
  fetchingInProgress = false;

  constructor(private itemService: ItemService, private messageService: MessageService, private router: Router) { }

  ngOnInit(): void {
    this.fetchNextPage()
  }

  ngOnDestroy(): void {
    this.itemServiceSubscription?.unsubscribe();
  }

  @HostListener("window:scroll")
  onScroll(): void {
    if (!this.fetchingInProgress && this.bottomReached() && !this.lastPage) {
      this.fetchingInProgress = true;
      this.fetchNextPage();
    }
  }

  bottomReached(): boolean {
    return (window.innerHeight + window.scrollY) >= document.body.offsetHeight;
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

  onFilterItems(filter: ItemFilter) {
    if (JSON.stringify(filter) === JSON.stringify(this.itemFilter)) {
      return;
    }

    this.itemFilter = filter;
    this.resetLoadedItems()
  }

  onSortItems(sorter: ItemSorter) {
    if (JSON.stringify(sorter) === JSON.stringify(this.itemSorter)) {
      return;
    }

    this.itemSorter = sorter;
    this.resetLoadedItems()
  }

  onCreateNewItem() {
    this.router.navigateByUrl("/items/new")
  }

}
