import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemDetailComponent } from './pages/item-detail/item-detail.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { EditItemComponent } from './pages/edit-item/edit-item.component';
import { CategoriesComponent } from './pages/categories/categories.component';
import { CategoryDetailComponent } from './pages/category-detail/category-detail.component';
import { EditCategoryComponent } from './pages/edit-category/edit-category.component';

const routes: Routes = [
  { path: 'categories/new', component: EditCategoryComponent },
  { path: 'categories/:id/edit', component: EditCategoryComponent },
  { path: 'categories/:id', component: CategoryDetailComponent },
  { path: 'categories', component: CategoriesComponent },
  { path: 'items/new', component: EditItemComponent },
  { path: 'items/:id/edit', component: EditItemComponent },
  { path: 'items/:id', component: ItemDetailComponent },
  { path: 'items', component: InventoryComponent },
  { path: '', component: InventoryComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
