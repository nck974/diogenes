import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
    selector: 'app-inventory-menu',
    templateUrl: './inventory-menu.component.html',
    styleUrls: ['./inventory-menu.component.scss'],
    standalone: false
})
export class InventoryMenuComponent {

  constructor(
    private readonly router: Router,
    private readonly authenticationService: AuthenticationService) { }

  onNavigateToCategories(): void {
    this.router.navigateByUrl("/categories");
  }

  onNavigateToLocations(): void {
    this.router.navigateByUrl("/locations");
  }

  onNavigateToBulkImport(): void {
    this.router.navigateByUrl("/items/edit/new/bulk");
  }

  onLogout(): void {
    this.authenticationService.logout();
    this.router.navigateByUrl("/");
  }
}
