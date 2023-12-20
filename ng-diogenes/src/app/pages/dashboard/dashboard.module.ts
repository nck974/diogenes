import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { DashboardMenuComponent } from './dashboard-menu/dashboard-menu.component';
import { DashboardComponent } from './dashboard.component';



@NgModule({
  declarations: [DashboardComponent, DashboardMenuComponent],
  imports: [
    CommonModule,

    // Local
    SharedComponentsModule,
    
    // Material
    MatIconModule,
    MatCardModule,
    MatToolbarModule,
    MatButtonModule,
    MatMenuModule,
  ],
  exports: [
    DashboardComponent
  ]
})
export class DashboardModule { }
