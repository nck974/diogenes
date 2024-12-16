import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-error-card',
    templateUrl: './error-card.component.html',
    styleUrls: ['./error-card.component.scss'],
    standalone: false
})
export class ErrorCardComponent {
  @Input() title = ""; 
  @Input() icon = "error"; 
  @Input() content = ""; 
}
