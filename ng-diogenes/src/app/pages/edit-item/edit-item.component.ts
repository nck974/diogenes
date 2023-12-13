import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-item',
  templateUrl: './edit-item.component.html',
  styleUrls: ['./edit-item.component.scss']
})
export class EditItemComponent implements OnInit {

  singleItemEdit = true;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.checkBulkUrl();
  }

  private checkBulkUrl() {
    const currentUrl = this.router.url;
    if (currentUrl.includes('bulk')) {
      this.singleItemEdit = false;
    }
  }

  returnToInventory(itemCreated: boolean): void {
    if (itemCreated) {
      this.router.navigateByUrl("/items");
    }
  }

}
