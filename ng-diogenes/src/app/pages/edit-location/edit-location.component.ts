import { Component } from '@angular/core';
import { Location as LocationCommon } from '@angular/common';
import { Observable, Subscription, catchError, finalize, map, of, switchMap, take, throwError } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Location } from 'src/app/models/Location';
import { LocationService } from 'src/app/shared/services/location.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { MessageService } from 'src/app/shared/services/message.service';
import { getMaterialIcons } from 'src/app/utils/material-icons';

@Component({
  selector: 'app-edit-location',
  templateUrl: './edit-location.component.html',
  styleUrls: ['./edit-location.component.scss']
})
export class EditLocationComponent {
  initializationSubscription?: Subscription;

  iconList: string[] = getMaterialIcons();

  locationForm: FormGroup;
  locations?: Location[];
  location?: Location;
  initializationError?: string;
  isNewLocation: boolean = true;
  isLoading: boolean = true;

  constructor(
    private readonly fb: FormBuilder,
    private readonly locationService: LocationService,
    private readonly route: ActivatedRoute,
    private readonly messageService: MessageService,
    private readonly locationCommon: LocationCommon) {
    this.locationForm = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.maxLength(2000)]],
      icon: ['', [Validators.required]],
    });

  }
  ngOnDestroy(): void {
    this.initializationSubscription?.unsubscribe();
  }

  ngOnInit(): void {
    this.initializationSubscription = this.getLocationToEdit()
      .pipe(finalize(
        () => {
          console.log("Finalize");
          this.isLoading = false;
        }
      ))
      .subscribe((location: Location | undefined) => {
        if (location) {
          this.isNewLocation = false;
          this.location = location;
          this.initializeEditFormWithLocationData(location);
        }
      });
  }

  private getLocationToEdit(): Observable<Location | undefined> {
    return this.route.paramMap.pipe(
      take(1), // paramMap keeps emitting values
      map(params => this.getRouteParameters(params)),
      switchMap(locationId => {
        if (locationId) {
          return this.locationService.getLocation(locationId)
        }
        return throwError(() => new Error("Location ID not provided"));
      }
      ),
      catchError(error => {
        if (error.message !== "Location ID not provided") {
          this.initializationError = "The location to edit could not be found.";
          this.isNewLocation = true;
        }
        return of(undefined);
      })
    );
  }

  private getRouteParameters(params: ParamMap): number | undefined {
    const idString = params.get("id");
    if (idString) {
      const id = parseInt(idString);
      if (isNaN(id)) {
        this.initializationError = `Error: Invalid id  ${idString} received to edit the location`;
        throwError(() => new Error(this.initializationError));
      }
      return id;
    }

    return undefined;
  }


  private initializeEditFormWithLocationData(location: Location): void {
    this.locationForm.patchValue({
      name: location.name,
      description: location.description,
      icon: location.icon,
    });
  }

  onSubmit(): void {
    if (this.locationForm.valid) {

      this.isLoading = true;

      const newLocation: Location = this.locationForm.value as Location;

      // Select service
      let manageLocationObservable = this.locationService.postLocation(newLocation);
      let locationTypeMessage = "created";
      if (!this.isNewLocation) {
        newLocation.id = this.location?.id!;
        manageLocationObservable = this.locationService.updateLocation(newLocation);
        locationTypeMessage = "updated";
      }

      manageLocationObservable
        .pipe(
          catchError(error => {
            this.messageService.add(`Location ${newLocation.name} could not be ${locationTypeMessage}. ${error}`)
            return throwError(() => error);
          }),
          finalize(() => this.isLoading = false)
        )
        .subscribe(
          location => {
            this.messageService.add(`Location ${location.name} was ${locationTypeMessage}`);
            this.locationCommon.back();
          }
        );
    }
  }

  onNavigateBack(): void {
    this.locationCommon.back();
  }
}
