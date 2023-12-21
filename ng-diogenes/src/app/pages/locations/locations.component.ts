import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { Location } from 'src/app/models/Location';
import { LocationService } from 'src/app/shared/services/location.service';

@Component({
  selector: 'app-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.scss']
})
export class LocationsComponent implements OnInit {

  locations: Location[] = [];
  isLoading: boolean = false;
  constructor(private locationService: LocationService, private router: Router) { }

  ngOnInit(): void {
    this.fetchLocations();
  }

  private fetchLocations() {
    this.isLoading = true;
    this.locationService.getLocations()
      .pipe(finalize(() => this.isLoading = false))
      .subscribe((locations) => this.locations = locations);
  }

  onCreateNewLocation(): void {
    this.router.navigateByUrl("/locations/new");
  }
}
