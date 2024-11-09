import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import { ErrorCardComponent } from './error-card/error-card.component';
import { GoBackComponent } from './go-back/go-back.component';
import { LoadingSpinnerComponent } from './loading-spinner/loading-spinner.component';
import { LogoComponent } from './logo/logo.component';
import { MessageComponent } from './messages/message.component';



@NgModule({
  declarations: [
    LoadingSpinnerComponent,
    ErrorCardComponent,
    ConfirmationDialogComponent,
    MessageComponent,
    LogoComponent,
    GoBackComponent
  ],
  imports: [
    CommonModule,

    // Material
    MatDialogModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatIconModule,
    MatTooltipModule,
  ],
  exports: [
    LoadingSpinnerComponent,
    ErrorCardComponent,
    ConfirmationDialogComponent,
    MessageComponent,
    LogoComponent,
    GoBackComponent
  ]
})
export class SharedComponentsModule { }
