import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EditItemModule } from './pages/edit-item/edit-item.module';
import { EditCategoryModule } from './pages/edit-category/edit-category.module';
import { ItemDetailModule } from './pages/item-detail/item-detail.module';
import { CategoryDetailModule } from './pages/category-detail/category-detail.module';
import { CategoriesModule } from './pages/categories/categories.module';
import { InventoryModule } from './pages/inventory/inventory.module';
import { SharedComponentsModule } from './shared/components/shared.components.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,

    // Local modules
    CategoriesModule,
    CategoryDetailModule,
    EditItemModule,
    EditCategoryModule,
    InventoryModule,
    ItemDetailModule,

    SharedComponentsModule,

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
