import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemDetailComponent } from './pages/item-detail/item-detail.component';
import { InventoryComponent } from './pages/inventory/inventory.component';

const routes: Routes = [
  { path: 'items/:id', component: ItemDetailComponent },
  { path: 'items', component: InventoryComponent },
  { path: '', component: InventoryComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
