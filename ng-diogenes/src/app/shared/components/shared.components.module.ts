import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { LoadingSpinnerComponent } from './loading-spinner/loading-spinner.component';
import { ErrorCardComponent } from './error-card/error-card.component';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import { MessageComponent } from './messages/message.component';
import { MatButtonModule } from '@angular/material/button';



@NgModule({
  declarations: [
    LoadingSpinnerComponent,
    ErrorCardComponent,
    ConfirmationDialogComponent,
    MessageComponent,
  ],
  imports: [
    CommonModule,

    // Material
    MatDialogModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
  ],
  exports: [
    LoadingSpinnerComponent,
    ErrorCardComponent,
    ConfirmationDialogComponent,
    MessageComponent
  ]
})
export class SharedComponentsModule { }
