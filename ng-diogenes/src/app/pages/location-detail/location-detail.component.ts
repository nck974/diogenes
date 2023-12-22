import { Location as CommonLocation, } from '@angular/common';
import { Component, OnDestroy, OnInit, } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription, catchError, finalize, of } from 'rxjs';
import { Location } from 'src/app/models/Location';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { LocationService } from 'src/app/shared/services/location.service';
import { MessageService } from 'src/app/shared/services/message.service';

@Component({
  selector: 'app-location-detail',
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.scss']
})
export class LocationDetailComponent implements OnInit, OnDestroy {

  locationServiceSubscription?: Subscription;
  paramsSubscription?: Subscription;
  location?: Location;
  isLoading = false;
  constructor(
    private locationService: LocationService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private commonLocation: CommonLocation,
    public dialogService: MatDialog,
    private router: Router) {

  }

  ngOnInit(): void {
    this.getLocation();
  }

  ngOnDestroy(): void {
    this.locationServiceSubscription?.unsubscribe();
    this.paramsSubscription?.unsubscribe();
  }

  private getLocation(): void {
    this.isLoading = true;
    this.paramsSubscription = this.route.paramMap
      .pipe(
        finalize(() => this.isLoading = false),
      )
      .subscribe(param => {
        const idParam = param.get("id");
        if (idParam) {
          const id = parseInt(idParam);
          this.queryItem(id)
        }
      });
  }

  private queryItem(id: number): void {
    this.locationServiceSubscription = this.locationService.getLocation(id)
      .pipe(
        finalize(() => this.isLoading = false),
      )
      .subscribe(location => {
        this.location = location;
      })
  }

  private openDialog(): Observable<any> {
    const dialogRef = this.dialogService.open(ConfirmationDialogComponent, {
      data: { title: "Delete location?", content: `Do you really want to delete ${this.location!.name}?` },
    });

    return dialogRef.afterClosed();
  }

  onNavigateBack(): void {
    this.commonLocation.back();
  }

  onEditLocation(): void {
    this.router.navigateByUrl(`/locations/${this.location?.id}/edit`);
  }

  private deleteLocation() {
    this.isLoading = true;
    this.locationService.deleteLocation(this.location!.id)
      .pipe(
        finalize(() => this.isLoading = false),
        catchError((err) => {
          const errorMessage = err["error"]["message"];
          this.messageService.add(`The location could not be deleted. ${errorMessage}`);
          return of(1);
        })
      )
      .subscribe((result) => {
        if (result != 1) {
          this.messageService.add(`Location ${this.location!.name} was deleted`);

          this.onNavigateBack();
        }
      });
  }

  onDeleteLocation(): void {
    if (!this.location) {
      console.error("Trying to delete an location that is not defined");
    }

    this.openDialog().subscribe(result => {
      console.log('The dialog was closed: ' + result);
      if (result as boolean) {
        this.deleteLocation();
      }
    });

  }
}
