import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EditItemModule } from './pages/edit-item/edit-item.module';
import { EditCategoryModule } from './pages/edit-category/edit-category.module';
import { ItemDetailModule } from './pages/item-detail/item-detail.module';
import { CategoryDetailModule } from './pages/category-detail/category-detail.module';
import { CategoriesModule } from './pages/categories/categories.module';
import { InventoryModule } from './pages/inventory/inventory.module';
import { LoginModule } from './pages/login/login.module';
import { SharedComponentsModule } from './shared/components/shared.components.module';
import { AuthenticationInterceptor } from './shared/interceptors/authentication.interceptor';
import { CategoriesSummaryModule } from './pages/categories-summary/categories-summary.module';
import { DashboardModule } from './pages/dashboard/dashboard.module';
import { LocationsModule } from './pages/locations/locations.module';
import { LocationDetailModule } from './pages/location-detail/location-detail.module';
import { LocationsSummaryModule } from './pages/locations-summary/locations-summary.module';
import { EditLocationModule } from './pages/edit-location/edit-location.module';

@NgModule({
    declarations: [
        AppComponent,
    ],
    bootstrap: [AppComponent],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        // Local modules
        CategoriesModule,
        CategoryDetailModule,
        EditItemModule,
        EditCategoryModule,
        InventoryModule,
        ItemDetailModule,
        LoginModule,
        CategoriesSummaryModule,
        DashboardModule,
        LocationsModule,
        LocationDetailModule,
        LocationsSummaryModule,
        EditLocationModule,
        SharedComponentsModule
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthenticationInterceptor,
            multi: true
        },
        provideHttpClient(withInterceptorsFromDi())
    ]
})
export class AppModule { }
