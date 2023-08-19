import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { ItemInListComponent } from './components/item-in-list/item-in-list.component';
import { LoadingSpinnerComponent } from './components/loading-spinner/loading-spinner.component';
import { MessageComponent } from './components/messages/message.component';

@NgModule({
  declarations: [
    ItemInListComponent,
    AppComponent,
    InventoryComponent,
    ItemInListComponent,
    LoadingSpinnerComponent,
    MessageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
