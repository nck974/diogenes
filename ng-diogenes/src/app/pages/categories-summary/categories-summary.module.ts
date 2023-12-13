import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { SharedPipesModule } from 'src/app/shared/pipes/shared.pipes.module';
import { CategoriesSummaryComponent } from './categories-summary.component';
import { CategoriesSummaryMenuComponent } from './categories-summary-menu/categories-summary-menu.component';



@NgModule({
  declarations: [
    CategoriesSummaryComponent,
    CategoriesSummaryMenuComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,

    // Local
    SharedComponentsModule,
    SharedPipesModule,

    // External
    InfiniteScrollModule,

    // Material
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
    MatSelectModule,
    MatInputModule,
    MatCardModule,
    MatMenuModule,
    MatDialogModule,

  ],
  exports: [
    CategoriesSummaryComponent
  ]
})
export class CategoriesSummaryModule { }
