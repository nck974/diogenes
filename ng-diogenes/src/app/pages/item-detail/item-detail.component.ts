import { Component, OnDestroy, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, Router, } from '@angular/router';
import { Observable, Subscription, finalize } from 'rxjs';
import { Item } from 'src/app/models/Item';
import { ItemService } from 'src/app/shared/services/item.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-item-detail',
  templateUrl: './item-detail.component.html',
  styleUrls: ['./item-detail.component.scss']
})
export class ItemDetailComponent implements OnInit, OnDestroy {

  itemServiceSubscription?: Subscription;
  paramsSubscription?: Subscription;
  item?: Item;
  isLoading = false;
  constructor(
    private itemService: ItemService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private location: Location,
    public dialogService: MatDialog,
    private router: Router) {

  }

  ngOnInit(): void {
    this.getItem();
  }

  ngOnDestroy(): void {
    this.itemServiceSubscription?.unsubscribe();
    this.paramsSubscription?.unsubscribe();
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

  private openDialog(): Observable<any> {
    const dialogRef = this.dialogService.open(ConfirmationDialogComponent, {
      data: { title: "Delete item?", content: `Do you really want to delete ${this.item!.name}?` },
    });

    return dialogRef.afterClosed();
  }

  onNavigateBack(): void {
    this.location.back();
  }

  onEditItem(): void {
    this.router.navigateByUrl(`/items/edit/${this.item?.id}`);
  }

  private deleteItem() {
    this.itemService.deleteItem(this.item!.id)
      .subscribe(() => {
        this.messageService.add(`Item ${this.item!.name} was deleted`);

        this.onNavigateBack();
      });
  }

  onDeleteItem(): void {
    if (!this.item) {
      console.error("Trying to delete an item that is not defined");
    }

    this.openDialog().subscribe(result => {
      console.log('The dialog was closed: ' + result);
      if (result as boolean) {
        this.deleteItem();
      }
    });

  }

  getAvatarColor(): string {
    if (this.item?.category != null) {
      let color = this.item.category.color;
      const hexColorRegex = /^(?:[0-9a-fA-F]{3}){1,2}$/;
      if (hexColorRegex.test(color)) {
        return `#${color}`;
      }
    }
    return "#ddd";
  }

}
