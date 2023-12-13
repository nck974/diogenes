import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CategoryService } from 'src/app/shared/services/category.service';
import { Subscription, finalize } from 'rxjs';
import { Category } from 'src/app/models/Category';

@Component({
  selector: 'app-categories-summary',
  standalone: false,
  templateUrl: './categories-summary.component.html',
  styleUrl: './categories-summary.component.scss'
})
export class CategoriesSummaryComponent implements OnInit {

  isLoading = true;
  categorySubscription?: Subscription;
  categories?: Category[];

  constructor(private router: Router, private categoryService: CategoryService) { };

  ngOnDestroy(): void {
    this.categorySubscription?.unsubscribe();
  }

  ngOnInit(): void {
    this.categorySubscription = this.categoryService.getCategories()
      .pipe(
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe(categories => {
        this.categories = categories;
      });
  }

  onCreateNewItem(): void {
    this.router.navigateByUrl("/items/edit/new");
  }

  getAvatarColor(category: Category): string {
    let color = category.color;
    const hexColorRegex = /^(?:[0-9a-fA-F]{3}){1,2}$/;
    if (hexColorRegex.test(color)) {
      return `#${color}`;
    }

    return "#ddd";
  }

  onOpenCategory(category: Category): void{
    this.router.navigateByUrl(`/items?categoryId=${category.id}`,);
  }

}
