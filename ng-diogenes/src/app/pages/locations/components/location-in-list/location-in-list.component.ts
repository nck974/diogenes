import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from 'src/app/models/Location';

@Component({
    selector: 'app-location-in-list',
    templateUrl: './location-in-list.component.html',
    styleUrls: ['./location-in-list.component.scss'],
    standalone: false
})
export class LocationInListComponent {

  @Input() location!: Location;

  constructor(private router: Router) {

  }

  onOpenDetails() {
    this.router.navigate(["locations", this.location.id]);
  }

}
