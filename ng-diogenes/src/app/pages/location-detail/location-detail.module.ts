import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LocationDetailComponent } from './location-detail.component';
import { MatIconModule } from '@angular/material/icon';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';



@NgModule({
  declarations: [LocationDetailComponent],
  imports: [
    CommonModule,

    // Local
    SharedComponentsModule,
    
    // Material
    MatIconModule,
    MatCardModule,
    MatToolbarModule,
    MatButtonModule,
  ],
  exports: [
    LocationDetailComponent
  ]
})
export class LocationDetailModule { }
