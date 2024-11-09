import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriesComponent } from './categories.component';
import { CategoryInListComponent } from './components/category-in-list/category-in-list.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';



@NgModule({
  declarations: [
    CategoriesComponent,
    CategoryInListComponent
  ],
  imports: [
    CommonModule,

    // Local
    SharedComponentsModule,

    // Material
    MatToolbarModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
  ],
  exports: [
    CategoriesComponent
  ]
})
export class CategoriesModule { }
