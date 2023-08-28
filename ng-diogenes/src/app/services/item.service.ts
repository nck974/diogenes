import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, of, tap } from 'rxjs';
import { Page } from '../models/Page';
import { Item } from '../models/Item';
import { ItemFilter } from '../models/ItemFilter';
import { ErrorHandlerService } from './error-handler.service';
import { ItemSorter } from '../models/ItemSorter';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private backendUrl = environment.diogenesBackendURL;
  private urlPath = "api/v1/item";
  private url = `${this.backendUrl}/${this.urlPath}`;


  constructor(private httpClient: HttpClient, private errorHandler: ErrorHandlerService) { }

  private createEmptyPage(): Page<Item> {
    const page: Page<Item> = {
      content: [],
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

  private applySorting(url: string, itemSorter?: ItemSorter): string {

    if (itemSorter) {
      url += `&sort=${itemSorter.field}&sortDirection=${itemSorter.direction}`;
    }

    return url;
  }

  getItems(page: number = 0, itemFilter?: ItemFilter, itemSorter?: ItemSorter): Observable<Page<Item>> {
    const pageSize = 10;
    let url = `${this.url}/?offset=${page}&pageSize=${pageSize}`;

    url = this.applyFilters(url, itemFilter)
    url = this.applySorting(url, itemSorter)

    console.log(url);
    return this.httpClient.get<Page<Item>>(url)
      .pipe(
        map((page) => {
          const mappedItems: Item[] = page.content.map((item) => ({ ...item }));
          page.content = mappedItems;
          return page;
        }),
        tap((page) => console.log(page)),
        catchError(this.errorHandler.handleError<Page<Item>>("getItems", this.createEmptyPage()
        )));
  }

  getItem(itemId: number): Observable<Item> {
    let url = `${this.url}/${itemId}`;

    console.log(url);

    return this.httpClient.get<Item>(url)
      .pipe(
        map((rawItem) => {
          const item: Item = { ...rawItem };
          return item;
        }),
        tap((item) => console.log(item)),
        catchError(this.errorHandler.handleError<Item>("getItem"))
      );
  }

  deleteItem(id: number): Observable<any> {
    const url = `${this.url}/${id}`;

    console.log(url);

    return this.httpClient.delete(url)
      .pipe(
        tap(_ => console.log(`Item ${id} deleted`)),
        catchError(this.errorHandler.handleError<any>("deleteItem", [])));
  }

  postItem(item: Item): Observable<Item> {
    let url = `${this.url}/`;

    console.log(url);

    let data = { ...item };

    return this.httpClient.post<Item>(url, data)
      .pipe(
        map((rawItem) => {
          const item: Item = { ...rawItem };
          return item;
        }),
        tap((item) => console.log(item)),
        catchError(this.errorHandler.handleError<Item>("postItem"))
      );
  }

  updateItem(item: Item): Observable<Item> {
    let url = `${this.url}/${item.id}`;

    console.log(url);

    let data = { ...item };

    return this.httpClient.put<Item>(url, data)
      .pipe(
        map((rawItem) => {
          const item: Item = { ...rawItem };
          return item;
        }),
        tap((item) => console.log(item)),
        catchError(this.errorHandler.handleError<Item>("updateItem"))
      );
  }
}
