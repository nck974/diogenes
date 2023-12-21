import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriesSummaryComponent } from './pages/categories-summary/categories-summary.component';
import { CategoriesComponent } from './pages/categories/categories.component';
import { CategoryDetailComponent } from './pages/category-detail/category-detail.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { EditCategoryComponent } from './pages/edit-category/edit-category.component';
import { EditSingleItemComponent } from './pages/edit-item/components/edit-single-item/edit-single-item.component';
import { EditItemComponent } from './pages/edit-item/edit-item.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { ItemDetailComponent } from './pages/item-detail/item-detail.component';
import { LocationsComponent } from './pages/locations/locations.component';
import { LoginComponent } from './pages/login/login.component';

const routes: Routes = [
  { path: 'categories/new', component: EditCategoryComponent },
  { path: 'categories/:id/edit', component: EditCategoryComponent },
  { path: 'categories/:id', component: CategoryDetailComponent },
  { path: 'categories', component: CategoriesComponent },
  { path: 'locations', component: LocationsComponent },
  {
    path: 'items/edit', component: EditItemComponent,
    children: [
      { path: '', redirectTo: 'new', pathMatch: 'full' },
      { path: 'new', component: EditSingleItemComponent, pathMatch: 'full' },
      { path: 'new/bulk', component: EditSingleItemComponent },
      { path: ':id', component: EditSingleItemComponent },
    ]
  },
  { path: 'items/:id', component: ItemDetailComponent },
  { path: 'items', component: InventoryComponent },
  { path: 'summary-categories', component: CategoriesSummaryComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'home', redirectTo: "dashboard" },
  { path: '', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
