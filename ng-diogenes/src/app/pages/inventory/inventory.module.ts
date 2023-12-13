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
import { InventoryFilterComponent } from './components/inventory-filter/inventory-filter.component';
import { SelectFilterComponent } from './components/inventory-filter/select-filter/select-filter.component';
import { TextFilterComponent } from './components/inventory-filter/text-filter/text-filter.component';
import { InventoryMenuComponent } from './components/inventory-menu/inventory-menu.component';
import { InventorySorterComponent } from './components/inventory-sorter/inventory-sorter.component';
import { ItemInListComponent } from './components/item-in-list/item-in-list.component';
import { InventoryComponent } from './inventory.component';
import { LayoutModule } from '@angular/cdk/layout';



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
    MatDialogModule,
    LayoutModule

  ],
  exports: [
    InventoryComponent,
  ]
})
export class InventoryModule { }
