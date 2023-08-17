import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Item } from 'src/app/models/Item';
import { ItemService } from 'src/app/services/item.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss']
})
export class InventoryComponent implements OnInit, OnDestroy {
  itemServiceSubscription?: Subscription;
  items: Item[] = [];

  constructor(private itemService: ItemService) { }

  ngOnInit(): void {
    this.itemService.getItems()
      .subscribe(page => { 
        console.log(page); 
        const mappedItems: Item[] = page.content.map(item => ({ ...item }));
        this.items.push(...mappedItems);
          console.log(this.items);
      });
  }

  ngOnDestroy(): void {
    if (this.itemServiceSubscription != null) {
      this.itemServiceSubscription.unsubscribe();
    }
  }
}
