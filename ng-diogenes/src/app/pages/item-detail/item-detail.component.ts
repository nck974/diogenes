import { Location } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router, } from '@angular/router';
import { Observable, Subscription, finalize } from 'rxjs';
import { Item } from 'src/app/models/Item';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ItemService } from 'src/app/shared/services/item.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { getColorOrDefault } from 'src/app/utils/color';

@Component({
    selector: 'app-item-detail',
    templateUrl: './item-detail.component.html',
    styleUrls: ['./item-detail.component.scss'],
    standalone: false
})
export class ItemDetailComponent implements OnInit, OnDestroy {

  itemServiceSubscription?: Subscription;
  paramsSubscription?: Subscription;
  item?: Item;
  isLoading = false;
  constructor(
    private readonly itemService: ItemService,
    private readonly messageService: MessageService,
    private readonly route: ActivatedRoute,
    private readonly location: Location,
    private readonly router: Router,
    public dialogService: MatDialog,
  ) {

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

  onEditItem(): void {
    this.router.navigateByUrl(`/items/edit/${this.item?.id}`);
  }

  private deleteItem() {
    this.itemService.deleteItem(this.item!.id)
      .subscribe(() => {
        this.messageService.add(`Item ${this.item!.name} was deleted`);

        this.location.back();
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
    let color = "";
    if (this.item?.category != null) {
      color = getColorOrDefault(this.item?.category.color);
    } else {
      color = getColorOrDefault();
    }
    return color;
  }



}
