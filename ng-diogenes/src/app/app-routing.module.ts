import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemDetailComponent } from './pages/item-detail/item-detail.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { EditItemComponent } from './pages/edit-item/edit-item.component';
import { CategoriesComponent } from './pages/categories/categories.component';
import { CategoryDetailComponent } from './pages/category-detail/category-detail.component';
import { EditCategoryComponent } from './pages/edit-category/edit-category.component';
import { EditSingleItemComponent } from './pages/edit-item/components/edit-single-item/edit-single-item.component';
import { LoginComponent } from './pages/login/login.component';

const routes: Routes = [
  { path: 'categories/new', component: EditCategoryComponent },
  { path: 'categories/:id/edit', component: EditCategoryComponent },
  { path: 'categories/:id', component: CategoryDetailComponent },
  { path: 'categories', component: CategoriesComponent },
  {
    path: 'items/edit', component: EditItemComponent,
    children: [
      { path: '', redirectTo: 'new', pathMatch: 'full'},
      { path: 'new', component: EditSingleItemComponent, pathMatch: 'full'},
      { path: 'new/bulk', component: EditSingleItemComponent },
      { path: ':id', component: EditSingleItemComponent },
    ]
  },
  { path: 'items/:id', component: ItemDetailComponent },
  { path: 'items', component: InventoryComponent },
  { path: '', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
