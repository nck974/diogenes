import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { ItemInListComponent } from './components/item-in-list/item-in-list.component';
import { LoadingSpinnerComponent } from './components/loading-spinner/loading-spinner.component';
import { MessageComponent } from './components/messages/message.component';
import { InventoryFilterComponent } from './components/inventory-filter/inventory-filter.component';
import { TextFilterComponent } from './components/inventory-filter/text-filter/text-filter.component';
import { CapitalizeFirstLetterPipe } from './pipes/capitalize-first-letter.pipe';
import { KeysPipe } from './pipes/keys.pipe';

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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
