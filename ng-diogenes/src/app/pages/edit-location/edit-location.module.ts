import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditLocationComponent } from './edit-location.component';
import { MAT_COLOR_FORMATS, NGX_MAT_COLOR_FORMATS, NgxMatColorPickerModule } from '@angular-material-components/color-picker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { MatSelectModule } from '@angular/material/select';



@NgModule({
  declarations: [EditLocationComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,

    // Internal
    SharedComponentsModule,

    // Material
    MatFormFieldModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,

  ],
  exports: [
    EditLocationComponent
  ],
  providers: [{ provide: MAT_COLOR_FORMATS, useValue: NGX_MAT_COLOR_FORMATS }],
})
export class EditLocationModule { }
