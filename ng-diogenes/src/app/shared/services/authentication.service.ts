import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, } from 'rxjs';
import { User } from 'src/app/models/User';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private static tokenName = "access_token";
  private static backendUrl = environment.diogenesBackendURL;
  private static urlPath = "authenticate";
  private url = `${AuthenticationService.backendUrl}/${AuthenticationService.urlPath}`;

  constructor(private http: HttpClient) { }


  login(user: User): Observable<string> {
    // Workaround to ignore JSON default validation as it comes as a plain string
    const httpOptions = { responseType: 'text' as 'json' }

    return this.http.post<string>(this.url, user, httpOptions).pipe(
      tap((token: string) => {
        this.storeToken(token);
      })
    );
  }

  logout(): void {
    this.removeToken();
  }

  getToken(): string | null {
    return this.readToken();
  }

  private readToken(): string | null {

    return localStorage.getItem(AuthenticationService.tokenName);
  }

  private storeToken(token: string): void {
    localStorage.setItem(AuthenticationService.tokenName, token);
  }

  private removeToken(): void {
    localStorage.removeItem(AuthenticationService.tokenName);
  }

}
