import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { MessageService } from 'src/app/services/message.service';

const MESSAGE_TIMEOUT_MS = 5000;

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent implements OnInit {
  private messageSubscription?: Subscription;

  constructor(public messageService: MessageService) { }

  ngOnInit() {
    this.messageSubscription = this.messageService.messagesAdded$.subscribe(messages => {
      messages.forEach(message => {
        this.scheduleMessageClose(message);
      });
    });
  }

  ngOnDestroy() {
    this.messageSubscription?.unsubscribe();
  }

  scheduleMessageClose(message: string) {
    setTimeout(() => {
      this.messageService.removeMessage(message);
    }, MESSAGE_TIMEOUT_MS);
  }

  closeMessage(message: string) {
    this.messageService.removeMessage(message);
  };
}
