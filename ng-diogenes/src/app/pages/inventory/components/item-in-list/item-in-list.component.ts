import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Item } from 'src/app/models/Item';
import { BreakpointObserver } from '@angular/cdk/layout';
import { Subscription } from 'rxjs';
import { getColorOrDefault } from 'src/app/utils/color';

@Component({
    selector: 'app-item-in-list',
    templateUrl: './item-in-list.component.html',
    styleUrls: ['./item-in-list.component.scss'],
    standalone: false
})
export class ItemInListComponent implements OnInit, OnDestroy {

  @Input() item!: Item;
  isLargeScreen: boolean = this.breakpointObserver.isMatched('(min-width: 768px)');
  screenResizingSubscription?: Subscription;

  constructor(private router: Router, private breakpointObserver: BreakpointObserver) {

  }

  ngOnDestroy(): void {
    this.screenResizingSubscription?.unsubscribe();
  }

  ngOnInit(): void {
    this.screenResizingSubscription = this.breakpointObserver.observe('(min-width: 768px)').subscribe(result => {
      this.isLargeScreen = result.matches;
    });
  }

  onOpenDetails() {
    this.router.navigate(["items", this.item.id]);
  }

  getAvatarColor(): string {
    let color = "";
    if (this.item.category) {
      color = getColorOrDefault(this.item.category.color);
    } else {
      color = getColorOrDefault();
    }
    return color;
  }
}
