import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-locations-summary-menu',
  standalone: false,
  templateUrl: './locations-summary-menu.component.html',
  styleUrl: './locations-summary-menu.component.scss'
})
export class LocationsSummaryMenuComponent {

  constructor(private router: Router, private authenticationService: AuthenticationService) { }

  onNavigateToLocations(): void {
    this.router.navigateByUrl("/locations");
  }

  onLogout(): void {
    this.authenticationService.logout();
    this.router.navigateByUrl("/");
  }

}

