import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatStepperModule } from '@angular/material/stepper';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { SharedPipesModule } from 'src/app/shared/pipes/shared.pipes.module';
import { EditBulkComponent } from './components/edit-bulk/edit-bulk.component';
import { EditItemImageComponent } from './components/edit-item-image/edit-item-image.component';
import { EditSingleItemComponent } from './components/edit-single-item/edit-single-item.component';
import { EditItemComponent } from './edit-item.component';


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
    SharedPipesModule,

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
    MatTooltipModule,
    MatSlideToggleModule
  ]
})
export class EditItemModule { }
