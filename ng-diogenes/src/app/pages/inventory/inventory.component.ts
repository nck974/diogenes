import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { Subscription, catchError, finalize } from 'rxjs';
import { Item } from 'src/app/models/Item';
import { ItemFilter } from 'src/app/models/ItemFilter';
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

  items: Item[] = [];
  isLoading = false;
  fetchingInProgress = false;

  constructor(private itemService: ItemService, private messageService: MessageService) { }

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
    this.itemServiceSubscription = this.itemService.getItems(nextPage, this.itemFilter)
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

  onDeleteItem(item: Item) {
    this.isLoading = true;
    this.itemService.deleteItem(item.id).pipe(
      catchError((err) => {
        console.log("Error deleting: " + err);
        return [];
      }),
      finalize(() => this.isLoading = false)
    ).subscribe(
      () => {
        const index = this.items.findIndex(x => x.id === item.id);
        if (index !== -1) {
          this.items.splice(index, 1);
        }
        this.messageService.add(`The item "${item.name}" was deleted.`);
      }
    );
  }

  resetLoadedItems() {
    this.currentPage = 0;
    this.lastPage = false;
    this.items.splice(0, this.items.length);
    this.fetchNextPage();
  }

  onFilterItems(filter: ItemFilter) {
    if (JSON.stringify(filter) === JSON.stringify(this.itemFilter)) {
      return;
    }

    this.isLoading = true;
    this.itemFilter = filter;
    this.resetLoadedItems()
    this.isLoading = false;

  }

}