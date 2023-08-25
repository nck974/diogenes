import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  /**
  * Handle Http operation that failed.
  * Let the app continue.
  *
  * @param operation - name of the operation that failed
  * @param result - optional value to return as the observable result
  */
  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(`${operation} failed:`);
      console.error(error);

      // Let the app keep running by returning an empty result.
      if (!result){
        return throwError(() => new Error(`${operation} could not be executed`));
      }
      return of(result as T);
    };
  }
}
