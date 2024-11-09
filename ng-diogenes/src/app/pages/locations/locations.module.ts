import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LocationsComponent } from './locations.component';
import { LocationInListComponent } from './components/location-in-list/location-in-list.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';



@NgModule({
  declarations: [
    LocationsComponent,
    LocationInListComponent
  ],
  imports: [
    CommonModule,

    // Local
    SharedComponentsModule,

    // Material
    MatToolbarModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
  ],
  exports: [
    LocationsComponent
  ]
})
export class LocationsModule { }
