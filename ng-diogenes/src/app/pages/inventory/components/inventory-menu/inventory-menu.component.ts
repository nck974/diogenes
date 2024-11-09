import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-inventory-menu',
  templateUrl: './inventory-menu.component.html',
  styleUrls: ['./inventory-menu.component.scss']
})
export class InventoryMenuComponent {

  constructor(
    private readonly router: Router,
    private readonly authenticationService: AuthenticationService) { }

  onNavigateToCategories(): void {
    this.router.navigateByUrl("/categories");
  }

  onNavigateToBulkImport(): void {
    this.router.navigateByUrl("/items/edit/new/bulk");
  }

  onLogout(): void {
    this.authenticationService.logout();
    this.router.navigateByUrl("/");
  }
}
