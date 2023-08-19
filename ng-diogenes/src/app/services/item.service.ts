import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, of, tap } from 'rxjs';
import { Page } from '../models/Page';
import { Item } from '../models/Item';
import { ItemFilter } from '../models/ItemFilter';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private backendUrl = "http://localhost:8080/diogenes";
  private urlPath = "api/v1/item";
  private url = `${this.backendUrl}/${this.urlPath}`;


  constructor(private httpClient: HttpClient) { }

  private createEmptyPage(): Page<Item> {
    const page: Page<Item> = {
      content: [],          // An array of your data items (MyItemType[])
      totalElements: 0,    // Total number of items in the dataset
      totalPages: 0,        // Total number of pages
      size: 0,              // Number of items per page
      number: 0,            // Current page number (0-based)
      sort: null,           // Sorting information (if applicable)
      first: true,          // Whether it's the first page
      last: true,          // Whether it's the last page
      numberOfElements: 0,  // Number of elements in the current page
      pageable: null,       // Information about pagination (if needed)
      empty: true          // Whether the content is empty
    };
    return page;
  }


  private applyFilters(url: string, itemFilter?: ItemFilter): string {

    if (itemFilter) {
      for (const key of Object.keys(itemFilter) as (keyof ItemFilter)[]) {
        const value = itemFilter[key];
        console.log(`Property Name: ${key}, Value: ${value}`);
        if (value && value != "") {

          url += `&${key}=${value}`;
        }
      }
    }

    return url;
  }

  getItems(page: number = 0, itemFilter?: ItemFilter): Observable<Page<Item>> {
    let url = `${this.url}/?offset=${page}`;

    url = this.applyFilters(url, itemFilter)

    console.log(url);
    return this.httpClient.get<Page<Item>>(url)
      .pipe(
        map((page) => {
          const mappedItems: Item[] = page.content.map((item) => ({ ...item }));
          page.content = mappedItems;
          return page;
        }),
        tap((page) => console.log(page)),
        catchError(this.handleError<Page<Item>>("getItems", this.createEmptyPage()
        )));
  }

  deleteItem(id: number): Observable<any> {
    const url = `${this.url}/${id}`;
    console.log(url);
    return this.httpClient.delete(url)
      .pipe(
        tap(_ => console.log(`Item ${id} deleted`)),
        catchError(this.handleError<any>("deleteItem", [])));
  }

  /**
  * Handle Http operation that failed.
  * Let the app continue.
  * TODO: Cleanup this to somewhere else
  *
  * @param operation - name of the operation that failed
  * @param result - optional value to return as the observable result
  */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

}
