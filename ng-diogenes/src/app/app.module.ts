import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';

import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';

import { InfiniteScrollModule } from "ngx-infinite-scroll";

import { MAT_COLOR_FORMATS, NgxMatColorPickerModule, NGX_MAT_COLOR_FORMATS } from '@angular-material-components/color-picker';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { ItemDetailComponent } from './pages/item-detail/item-detail.component';
import { ItemInListComponent } from './components/item-in-list/item-in-list.component';
import { LoadingSpinnerComponent } from './components/loading-spinner/loading-spinner.component';
import { MessageComponent } from './components/messages/message.component';
import { InventoryFilterComponent } from './components/inventory-filter/inventory-filter.component';
import { TextFilterComponent } from './components/inventory-filter/text-filter/text-filter.component';
import { CapitalizeFirstLetterPipe } from './pipes/capitalize-first-letter.pipe';
import { KeysPipe } from './pipes/keys.pipe';
import { EditItemComponent } from './pages/edit-item/edit-item.component';
import { InventorySorterComponent } from './components/inventory-sorter/inventory-sorter.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ErrorCardComponent } from './components/error-card/error-card.component';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { CategoriesComponent } from './pages/categories/categories.component';
import { InventoryMenuComponent } from './components/inventory-menu/inventory-menu.component';
import { CategoryInListComponent } from './components/category-in-list/category-in-list.component';
import { CategoryDetailComponent } from './pages/category-detail/category-detail.component';
import { EditCategoryComponent } from './pages/edit-category/edit-category.component';
import { SelectFilterComponent } from './components/inventory-filter/select-filter/select-filter.component';
import { GenerateSelectBoxOptionsPipe } from './pipes/generate-select-box-options.pipe';

@NgModule({
  declarations: [
    ItemInListComponent,
    AppComponent,
    InventoryComponent,
    ItemInListComponent,
    LoadingSpinnerComponent,
    MessageComponent,
    InventoryFilterComponent,
    TextFilterComponent,
    CapitalizeFirstLetterPipe,
    KeysPipe,
    ItemDetailComponent,
    EditItemComponent,
    InventorySorterComponent,
    ErrorCardComponent,
    ConfirmationDialogComponent,
    CategoriesComponent,
    InventoryMenuComponent,
    CategoryInListComponent,
    CategoryDetailComponent,
    EditCategoryComponent,
    SelectFilterComponent,
    GenerateSelectBoxOptionsPipe,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    InfiniteScrollModule,
    NgxMatColorPickerModule,
    // Material:
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatSelectModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDialogModule,
    MatMenuModule,
  ],
  providers: [{ provide: MAT_COLOR_FORMATS, useValue: NGX_MAT_COLOR_FORMATS }],
  bootstrap: [AppComponent]
})
export class AppModule { }
