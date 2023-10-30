import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { SharedComponentsModule } from 'src/app/shared/components/shared.components.module';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';


@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule,
    FormsModule,

    // Internal
    SharedComponentsModule,

    // External
    MatIconModule,
    MatCardModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule

  ],
  exports: [
    LoginComponent
  ]
})
export class LoginModule { }
