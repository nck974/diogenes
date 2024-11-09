import { HttpClient } from '@angular/common/http';
import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Observable, map, of } from 'rxjs';

@Pipe({
  name: 'secureImage'
})
export class SecureImagePipe implements PipeTransform {

  constructor(
    private readonly http: HttpClient,
    private readonly sanitizer: DomSanitizer) { }

  /// This was extracted from_
  /// https://stackoverflow.com/questions/47811784/passing-authorization-header-for-images-src-to-remote-server-in-ionic-page/50463201#50463201
  transform(url?: string): Observable<SafeUrl> {
    if (!url) {
      return of();
    }

    return this.http
      .get(url, { responseType: 'blob' })
      .pipe(map(val => this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(val))));
  }
}
