import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditCategoryComponent } from './edit-category.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { NgxColorsModule } from 'ngx-colors';



@NgModule({
  declarations: [EditCategoryComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,

    // Internal
    SharedComponentsModule,

    // External
    NgxColorsModule,

    // Material
    MatFormFieldModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,

  ],
  exports: [
    EditCategoryComponent
  ],
  providers: [],
})
export class EditCategoryModule { }
