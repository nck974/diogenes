import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { DashboardItem } from 'src/app/models/DashboardItem';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
  isLoading = false;
  dashboardItems: DashboardItem[] = [
    {
      name: "Inventory",
      route: "/items",
      icon: "home"
    },
    {
      name: "Categories overview",
      route: "/summary-categories",
      icon: "category"
    }
  ]

  constructor(private router: Router) { }
  onOpenRoute(route: string): void {
    this.router.navigateByUrl(route);
  }
}
