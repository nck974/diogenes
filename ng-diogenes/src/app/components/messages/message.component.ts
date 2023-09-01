import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
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

  constructor(public messageService: MessageService, private snackBarService: MatSnackBar) { }

  ngOnInit() {
    this.messageSubscription = this.messageService.messagesAdded$.subscribe(messages => {
      messages.forEach(message => {
        this.onNewMessage(message);
      });
    });
  }

  onNewMessage(message: string) {
    this.snackBarService.open(message, "Close", { duration: MESSAGE_TIMEOUT_MS })
  }

  ngOnDestroy() {
    this.messageSubscription?.unsubscribe();
  }

}
