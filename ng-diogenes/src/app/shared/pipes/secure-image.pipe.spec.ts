import { HttpClient } from '@angular/common/http';
import { SecureImagePipe } from './secure-image.pipe';
import { DomSanitizer } from '@angular/platform-browser';

describe('SecureImagePipe', () => {
  it('create an instance', () => {
    const http = {} as HttpClient; // Replace with your HttpClient instance
    const sanitizer = {} as DomSanitizer; // Replace with your DomSanitizer instance
    const pipe = new SecureImagePipe(http, sanitizer);
    expect(pipe).toBeTruthy();
  });
});
