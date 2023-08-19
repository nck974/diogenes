import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  messages: string[] = [];

  // Broadcast messages
  private messagesAddedSubject = new Subject<string[]>();
  messagesAdded$ = this.messagesAddedSubject.asObservable();

  add(message: string) {
    this.messages.push(message);
    this.messagesAddedSubject.next(this.messages.slice());
  }

  clear() {
    this.messages = [];
    this.messagesAddedSubject.next([]);
  }

  removeMessage(message: string) {
    let index = this.messages.findIndex((x) => x === message);
    if (index != -1) {
      this.messages.splice(index, 1);
      this.messagesAddedSubject.next(this.messages.slice());
    }
  }

}
