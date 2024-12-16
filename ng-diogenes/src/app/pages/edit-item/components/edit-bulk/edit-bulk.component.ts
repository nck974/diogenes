import { Component, ViewChild } from '@angular/core';
import { MatStepper } from '@angular/material/stepper';
import { Router } from '@angular/router';
import { ImageTransfer } from 'src/app/models/ImageTransfer';

@Component({
  selector: 'app-edit-bulk',
  templateUrl: './edit-bulk.component.html',
  styleUrls: ['./edit-bulk.component.scss']
})
export class EditBulkComponent {
  @ViewChild('stepper') stepper?: MatStepper;

  completedSteps: boolean[] = [];
  selectedImages: ImageTransfer[] = [];
  isDragging = false;
  isAiActive = false;

  constructor(private readonly router: Router) { }



  onFilesSelected(event: any) {
    const files: FileList = event.target.files;
    if (files) {
      for (const file of Array.from(files)) {
        this.readValidFile(file);
      }
    }
  }

  private readValidFile(file: File) {
    if (/image\/(gif|jpeg|png)/.exec(file.type)) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const displayableImage = e.target?.result as string;
        if (displayableImage) {
          const imageTransfer: ImageTransfer = { displayableImage: displayableImage, file: file };
          this.selectedImages?.push(imageTransfer);
        }
      };
      reader.readAsDataURL(file);
    }
  }

  onFinishStep(itemCreated: boolean, index: number): void {
    if (itemCreated) {
      this.stepper?.next();
      this.completedSteps[index] = true;
      if (index == this.selectedImages.length - 1) {
        this.router.navigateByUrl("/items");
      }
    }
  }

  onDrop(event: any) {
    event.preventDefault();

    if (event.dataTransfer.files) {
      for (const file of event.dataTransfer.files) {
        this.readValidFile(file)
      }
    }
    this.isDragging = false;
  }

  onDragOver(event: Event) {
    event.preventDefault();
    this.isDragging = true;
  }

  onDragLeave(event: Event) {
    event.preventDefault();
    this.isDragging = false;
  }

  isStepCompleted(index: number): boolean {
    return this.completedSteps[index] || false;
  }
}
