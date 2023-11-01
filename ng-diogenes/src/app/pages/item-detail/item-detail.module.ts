import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemDetailComponent } from './item-detail.component';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { SharedPipesModule } from 'src/app/shared/pipes/shared.pipes.module';



@NgModule({
  declarations: [ItemDetailComponent],
  imports: [
    CommonModule,

    // Internal
    SharedComponentsModule,
    SharedPipesModule,

    // External
    MatIconModule,
    MatCardModule,
    MatToolbarModule,
    MatButtonModule,
  ],
  exports: [
    ItemDetailComponent
  ]
})
export class ItemDetailModule { }
