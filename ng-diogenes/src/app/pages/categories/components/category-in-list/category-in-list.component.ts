import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/models/Category';

@Component({
  selector: 'app-category-in-list',
  templateUrl: './category-in-list.component.html',
  styleUrls: ['./category-in-list.component.scss']
})
export class CategoryInListComponent {

  @Input() category!: Category;

  constructor(private router: Router) {

  }

  onOpenDetails() {
    this.router.navigate(["categories", this.category.id]);
  }

  getAvatarColor(): string {
    if (this.category != null) {
      let color = this.category.color;
      const hexColorRegex = /^(?:[0-9a-fA-F]{3}){1,2}$/;
      if (hexColorRegex.test(color)) {
        return `#${color}`;
      }
    }
    return "#ddd";
  }
}
