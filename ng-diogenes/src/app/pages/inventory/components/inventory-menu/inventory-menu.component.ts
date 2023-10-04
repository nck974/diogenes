import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inventory-menu',
  templateUrl: './inventory-menu.component.html',
  styleUrls: ['./inventory-menu.component.scss']
})
export class InventoryMenuComponent {

  constructor(private router: Router) { }

  onNavigateToCategories() {
    this.router.navigateByUrl("/categories");
  }  
  
  onNavigateToBulkImport() {
    this.router.navigateByUrl("/items/edit/new/bulk");
  }
}
