import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, } from '@angular/router';
import { Subscription, finalize } from 'rxjs';
import { Item } from 'src/app/models/Item';
import { ItemService } from 'src/app/services/item.service';

@Component({
  selector: 'app-item-detail',
  templateUrl: './item-detail.component.html',
  styleUrls: ['./item-detail.component.scss']
})
export class ItemDetailComponent implements OnInit {

  itemServiceSubscription?: Subscription;
  paramsSubscription?: Subscription;
  item?: Item;
  isLoading = false;
  constructor(private itemService: ItemService, private route: ActivatedRoute, private location: Location) {

  }
  ngOnInit(): void {
    this.getItem();
  }

  private getItem(): void {
    this.isLoading = true;
    this.paramsSubscription = this.route.paramMap
      .pipe(
        finalize(() => this.isLoading = false),
      )
      .subscribe(param => {
        const idParam = param.get("id");
        if (idParam) {
          const id = parseInt(idParam);
          this.queryItem(id)
        }
      });
  }

  private queryItem(id: number): void {
    this.itemServiceSubscription = this.itemService.getItem(id)
      .pipe(
        finalize(() => this.isLoading = false),
      )
      .subscribe(item => {
        this.item = item;
      })
  }

  onNavigateBack(): void {
    this.location.back();
  }

}
