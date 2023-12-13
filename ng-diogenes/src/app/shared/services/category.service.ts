import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, tap } from 'rxjs';

import { Category } from '../../models/Category';
import { ErrorHandlerService } from './error-handler.service';
import { environment } from 'src/environments/environment';
import { CategorySummary } from 'src/app/models/CategorySummary';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private backendUrl = environment.diogenesBackendURL;
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

  getCategoriesSummary(): Observable<CategorySummary[]> {
    let url = `${this.url}/summary`;

    console.log(url);
    return this.httpClient.get<CategorySummary[]>(url)
      .pipe(
        map((rawSummary) => {
          const categories: CategorySummary[] = rawSummary.map((summary) => ({ ...summary }));
          return categories;
        }),
        tap((categories) => console.log(categories)),
        catchError(this.errorHandler.handleError<CategorySummary[]>("getCategoriesSummary", []))
      );
  }

  getCategory(categoryId: number): Observable<Category> {
    let url = `${this.url}/${categoryId}`;

    console.log(url);

    return this.httpClient.get<Category>(url)
      .pipe(
        map((rawCategory) => {
          const category: Category = { ...rawCategory };
          return category;
        }),
        tap((category) => console.log(category)),
        catchError(this.errorHandler.handleError<Category>("getCategory"))
      );
  }

  deleteCategory(id: number): Observable<any> {
    const url = `${this.url}/${id}`;

    console.log(url);

    return this.httpClient.delete(url)
      .pipe(
        tap(_ => console.log(`Category ${id} deleted`)),
        catchError(this.errorHandler.handleError<any>("deleteCategory")));
  }

  postCategory(category: Category): Observable<Category> {
    let url = `${this.url}/`;

    console.log(url);

    let data = { ...category };

    return this.httpClient.post<Category>(url, data)
      .pipe(
        map((rawCategory) => {
          const category: Category = { ...rawCategory };
          return category;
        }),
        tap((category) => console.log(category)),
        catchError(this.errorHandler.handleError<Category>("postCategory"))
      );
  }

  updateCategory(category: Category): Observable<Category> {
    let url = `${this.url}/${category.id}`;

    console.log(url);

    let data = { ...category };

    return this.httpClient.put<Category>(url, data)
      .pipe(
        map((rawCategory) => {
          const category: Category = { ...rawCategory };
          return category;
        }),
        tap((category) => console.log(category)),
        catchError(this.errorHandler.handleError<Category>("updateCategory"))
      );
  }
}
