import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ErrorHandlerService } from './error-handler.service';
import { catchError, Observable, tap } from 'rxjs';
import { RecognizedItem } from 'src/app/models/RecognizedItem';

@Injectable({
  providedIn: 'root'
})
export class AiService {
  private readonly backendUrl = environment.diogenesBackendURL;
  private readonly urlPath = "api/v1/ai";
  private readonly url = `${this.backendUrl}/${this.urlPath}`;


  constructor(
    private readonly httpClient: HttpClient,
    private readonly errorHandler: ErrorHandlerService) { }

  recognizeItemInImage(image: Blob): Observable<RecognizedItem> {
    const url = `${this.url}/recognize-item`;
    console.log(url);

    const data = new FormData();
    data.append('image', image);

    return this.httpClient.post<RecognizedItem>(url, data)
      .pipe(
        tap(item => console.log(item)),
        catchError(this.errorHandler.handleError<RecognizedItem>("postItem"))
      );
  }

}

