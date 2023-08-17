import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { Subscription, catchError } from 'rxjs';
import { Item } from 'src/app/models/Item';
import { ItemService } from 'src/app/services/item.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss']
})
export class InventoryComponent implements OnInit, OnDestroy {

  private itemServiceSubscription?: Subscription;
  private currentPage: number = 0;
  private lastPage: boolean = false
  items: Item[] = [];
  fetchingInProgress = false;

  constructor(private itemService: ItemService) { }

  ngOnInit(): void {
    this.fetchNextPage()
  }

  ngOnDestroy(): void {
    if (this.itemServiceSubscription != null) {
      this.itemServiceSubscription.unsubscribe();
    }
  }

  @HostListener("window:scroll", [])
  onScroll(): void {
    if (!this.fetchingInProgress && this.bottomReached() && !this.lastPage) {
      this.fetchingInProgress = true;
      this.fetchNextPage();
    }
  }

  bottomReached(): boolean {
    return (window.innerHeight + window.scrollY) >= document.body.offsetHeight;
  }

  fetchNextPage() {
    if (this.lastPage) {
      return;
    }

    const nextPage = this.currentPage;
    this.itemServiceSubscription = this.itemService.getItems(nextPage)
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



}
