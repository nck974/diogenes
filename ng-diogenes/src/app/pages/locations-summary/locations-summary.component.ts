import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocationService } from 'src/app/shared/services/location.service';
import { Subscription, finalize } from 'rxjs';
import { Location } from 'src/app/models/Location';
import { LocationSummary } from 'src/app/models/LocationSummary';

@Component({
  selector: 'app-locations-summary',
  standalone: false,
  templateUrl: './locations-summary.component.html',
  styleUrl: './locations-summary.component.scss'
})
export class LocationsSummaryComponent implements OnInit {

  isLoading = true;
  locationSubscription?: Subscription;
  locationsSummary?: LocationSummary[];

  constructor(private router: Router, private locationService: LocationService) { };

  ngOnDestroy(): void {
    this.locationSubscription?.unsubscribe();
  }

  ngOnInit(): void {
    this.locationSubscription = this.locationService.getLocationsSummary()
      .pipe(
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe(locationsSummary => {
        this.locationsSummary = locationsSummary;
      });
  }

  onCreateNewItem(): void {
    this.router.navigateByUrl("/items/edit/new");
  }

  onOpenLocation(location: Location): void {
    this.router.navigateByUrl(`/items?locationId=${location.id}`,);
  }

}
