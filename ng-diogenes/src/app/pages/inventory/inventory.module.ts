import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryComponent } from './inventory.component';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { ItemInListComponent } from './components/item-in-list/item-in-list.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { InventorySorterComponent } from './components/inventory-sorter/inventory-sorter.component';
import { InventoryMenuComponent } from './components/inventory-menu/inventory-menu.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { InventoryFilterComponent } from './components/inventory-filter/inventory-filter.component';
import { TextFilterComponent } from './components/inventory-filter/text-filter/text-filter.component';
import { SelectFilterComponent } from './components/inventory-filter/select-filter/select-filter.component';
import { MatCardModule } from '@angular/material/card';
import { SharedPipesModule } from 'src/app/shared/pipes/shared.pipes.module';
import { MatMenuModule } from '@angular/material/menu';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    InventoryComponent,
    ItemInListComponent,
    InventorySorterComponent,
    InventoryMenuComponent,
    InventoryFilterComponent,
    TextFilterComponent,
    SelectFilterComponent
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


  ],
  exports: [
    InventoryComponent,
  ]
})
export class InventoryModule { }
