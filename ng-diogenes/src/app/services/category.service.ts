import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, tap } from 'rxjs';

import { Category } from '../models/Category';
import { ErrorHandlerService } from './error-handler.service';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private backendUrl = "http://localhost:8080/diogenes";
  private urlPath = "api/v1/categories";
  private url = `${this.backendUrl}/${this.urlPath}`;


  constructor(private httpClient: HttpClient, private errorHandler: ErrorHandlerService) { }

  getCategories(): Observable<Category[]> {
    let url = `${this.url}/`;

    console.log(url);
    return this.httpClient.get<Category[]>(url)
      .pipe(
        map((rawCategories) => {
          const categories: Category[] = rawCategories.map((category) => ({ ...category }));
          return categories;
        }),
        tap((categories) => console.log(categories)),
        catchError(this.errorHandler.handleError<Category[]>("getCategories", []))
      );
  }
}
