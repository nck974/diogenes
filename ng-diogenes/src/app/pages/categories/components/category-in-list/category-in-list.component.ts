import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/models/Category';
import { getColorOrDefault } from 'src/app/utils/color';

@Component({
    selector: 'app-category-in-list',
    templateUrl: './category-in-list.component.html',
    styleUrls: ['./category-in-list.component.scss'],
    standalone: false
})
export class CategoryInListComponent {

  @Input() category!: Category;

  constructor(private router: Router) {

  }

  onOpenDetails() {
    this.router.navigate(["categories", this.category.id]);
  }

  getAvatarColor(): string {
    let color = "";
    if (this.category) {
      color = getColorOrDefault(this.category.color);
    } else {
      color = getColorOrDefault();
    }
    return color;
  }
}
