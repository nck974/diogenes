import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../shared/services/authentication.service';
import { User } from 'src/app/models/User';
import { finalize, catchError, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username?: string;
  password?: string;

  isLoading = false;
  error?: string;

  form: FormGroup;

  constructor(private fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router) {

    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login() {
    const currentValue = this.form.value;
    this.isLoading = true;
    this.error = undefined;
    if (currentValue.username && currentValue.password) {
      const user: User = { username: currentValue.username, password: currentValue.password };
      this.authenticationService.login(user)
        .pipe(
          finalize(() => {
            this.isLoading = false;
          }),
          catchError((err) => {
            this.setErrorMessage(err);
            return throwError(() => err)
          })
        )
        .subscribe(
          (_) => {
            this.router.navigateByUrl('/items');
          }
        );
    }
  }


  private setErrorMessage(err: any) {
    if (err instanceof HttpErrorResponse) {
      if (err.status === 401) {
        this.error = "It was not possible to log in with those credentials";
      } else {
        this.error = `Error trying to login (${err.status})`;
      }
    } else {
      this.error = "Unexpected error trying to login";
    }
  }

}
