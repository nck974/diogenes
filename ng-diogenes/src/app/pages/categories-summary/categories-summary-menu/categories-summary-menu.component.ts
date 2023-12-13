import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-categories-summary-menu',
  standalone: false,
  templateUrl: './categories-summary-menu.component.html',
  styleUrl: './categories-summary-menu.component.scss'
})
export class CategoriesSummaryMenuComponent {

  constructor(private router: Router, private authenticationService: AuthenticationService) { }

  onNavigateToCategories(): void {
    this.router.navigateByUrl("/categories");
  }

  onLogout(): void {
    this.authenticationService.logout();
    this.router.navigateByUrl("/");
  }

}

