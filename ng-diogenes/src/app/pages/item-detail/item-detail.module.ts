import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { SharedPipesModule } from 'src/app/shared/pipes/shared.pipes.module';
import { ItemDetailComponent } from './item-detail.component';



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
    MatTooltipModule,
  ],
  exports: [
    ItemDetailComponent
  ]
})
export class ItemDetailModule { }
