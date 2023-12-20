import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';

@Component({
  selector: 'app-dashboard-menu',
  standalone: false,
  templateUrl: './dashboard-menu.component.html',
  styleUrl: './dashboard-menu.component.scss'
})
export class DashboardMenuComponent {

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) { }

  onLogout(): void {
    this.authenticationService.logout();
    this.router.navigateByUrl("/");
  }

}
