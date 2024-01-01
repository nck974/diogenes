import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, tap } from 'rxjs';

import { Location } from '../../models/Location';
import { ErrorHandlerService } from './error-handler.service';
import { environment } from 'src/environments/environment';
import { LocationSummary } from 'src/app/models/LocationSummary';


@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private backendUrl = environment.diogenesBackendURL;
  private urlPath = "api/v1/locations";
  private url = `${this.backendUrl}/${this.urlPath}`;


  constructor(private httpClient: HttpClient, private errorHandler: ErrorHandlerService) { }

  getLocations(): Observable<Location[]> {
    let url = `${this.url}/`;

    console.log(url);
    return this.httpClient.get<Location[]>(url)
      .pipe(
        map((rawLocations) => {
          const locations: Location[] = rawLocations.map((location) => ({ ...location }));
          return locations;
        }),
        tap((locations) => console.log(locations)),
        catchError(this.errorHandler.handleError<Location[]>("getLocations", []))
      );
  }

  getLocationsSummary(): Observable<LocationSummary[]> {
    let url = `${this.url}/summary`;

    console.log(url);
    return this.httpClient.get<LocationSummary[]>(url)
      .pipe(
        map((rawSummary) => {
          const locations: LocationSummary[] = rawSummary.map((summary) => ({ ...summary }));
          return locations;
        }),
        tap((locations) => console.log(locations)),
        catchError(this.errorHandler.handleError<LocationSummary[]>("getLocationsSummary", []))
      );
  }

  getLocation(locationId: number): Observable<Location> {
    let url = `${this.url}/${locationId}`;

    console.log(url);

    return this.httpClient.get<Location>(url)
      .pipe(
        map((rawLocation) => {
          const location: Location = { ...rawLocation };
          return location;
        }),
        tap((location) => console.log(location)),
        catchError(this.errorHandler.handleError<Location>("getLocation"))
      );
  }

  deleteLocation(id: number): Observable<any> {
    const url = `${this.url}/${id}`;

    console.log(url);

    return this.httpClient.delete(url)
      .pipe(
        tap(_ => console.log(`Location ${id} deleted`)),
        catchError(this.errorHandler.handleError<any>("deleteLocation")));
  }

  postLocation(location: Location): Observable<Location> {
    let url = `${this.url}/`;

    console.log(url);

    let data = { ...location };

    return this.httpClient.post<Location>(url, data)
      .pipe(
        map((rawLocation) => {
          const location: Location = { ...rawLocation };
          return location;
        }),
        tap((location) => console.log(location)),
        catchError(this.errorHandler.handleError<Location>("postLocation"))
      );
  }

  updateLocation(location: Location): Observable<Location> {
    let url = `${this.url}/${location.id}`;

    console.log(url);

    let data = { ...location };

    return this.httpClient.put<Location>(url, data)
      .pipe(
        map((rawLocation) => {
          const location: Location = { ...rawLocation };
          return location;
        }),
        tap((location) => console.log(location)),
        catchError(this.errorHandler.handleError<Location>("updateLocation"))
      );
  }
}
