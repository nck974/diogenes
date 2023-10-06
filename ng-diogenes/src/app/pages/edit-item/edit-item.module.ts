import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditItemComponent } from './edit-item.component';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatStepperModule } from '@angular/material/stepper';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { EditItemImageComponent } from './components/edit-item-image/edit-item-image.component';
import { EditSingleItemComponent } from './components/edit-single-item/edit-single-item.component';
import { EditBulkComponent } from './components/edit-bulk/edit-bulk.component';


@NgModule({
  declarations: [
    EditItemComponent,
    EditItemImageComponent,
    EditSingleItemComponent,
    EditBulkComponent,
  ],
  imports: [
    // Angular
    CommonModule,
    FormsModule,
    ReactiveFormsModule,

    // Local
    SharedComponentsModule,

    // Material
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatSelectModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatStepperModule,
  ]
})
export class EditItemModule { }
