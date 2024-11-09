import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { EditLocationComponent } from './edit-location.component';



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
  providers: [],
})
export class EditLocationModule { }
