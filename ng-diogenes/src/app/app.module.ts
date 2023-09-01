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

import { InfiniteScrollModule } from "ngx-infinite-scroll";

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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    // Material:
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatSelectModule,
    MatCardModule,
    MatProgressSpinnerModule,
    InfiniteScrollModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
