import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable, finalize } from 'rxjs';
import { Item } from 'src/app/models/Item';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ItemService } from 'src/app/shared/services/item.service';
import { MessageService } from 'src/app/shared/services/message.service';

@Component({
  selector: 'app-edit-item-image',
  templateUrl: './edit-item-image.component.html',
  styleUrls: ['./edit-item-image.component.scss']
})
export class EditItemImageComponent {

  @Input() imagePath?: string;
  @Input({ required: true }) isLoading!: boolean;
  @Output() imageBlobChanged = new EventEmitter<Blob>();
  @Input() item?: Item;

  selectedImage?: string;

  constructor(private dialogService: MatDialog, private itemService: ItemService, private messageService: MessageService) { }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.imageBlobChanged.emit(file);
      if (file.type.match(/image\/(gif|jpeg|png)/)) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.selectedImage = e.target?.result as string;
        };
        reader.readAsDataURL(file);
      }
    }
  }

  onFileDeselected() {
    this.selectedImage = undefined;
    this.imageBlobChanged.emit(undefined);
  }

  private deleteImage() {
    this.isLoading = true;
    this.itemService.deleteItemImage(this.item!.id)
      .pipe(
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe(() => {
        this.messageService.add(`The ${this.item!.name} image was deleted`);

        this.imagePath = undefined;
      },
      );
  }

  private openConfirmDeleteDialog(): Observable<any> {
    const dialogRef = this.dialogService.open(ConfirmationDialogComponent, {
      data: { title: "Delete image?", content: `Do you really want to delete the image?` },
    });

    return dialogRef.afterClosed();
  }

  onDeleteImage(): void {
    if (!this.imagePath) {
      console.error("Trying to delete an image that is not defined");
      return;
    }

    this.openConfirmDeleteDialog().subscribe(result => {
      if (result as boolean) {
        this.deleteImage();
      }
    });
  }
}
