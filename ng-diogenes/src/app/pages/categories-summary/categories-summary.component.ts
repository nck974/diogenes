import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CategoryService } from 'src/app/shared/services/category.service';
import { Subscription, finalize } from 'rxjs';
import { Category } from 'src/app/models/Category';
import { CategorySummary } from 'src/app/models/CategorySummary';

@Component({
  selector: 'app-categories-summary',
  standalone: false,
  templateUrl: './categories-summary.component.html',
  styleUrl: './categories-summary.component.scss'
})
export class CategoriesSummaryComponent implements OnInit {

  isLoading = true;
  categorySubscription?: Subscription;
  categoriesSummary?: CategorySummary[];

  constructor(private readonly router: Router, private readonly categoryService: CategoryService) { };

  ngOnDestroy(): void {
    this.categorySubscription?.unsubscribe();
  }

  ngOnInit(): void {
    this.categorySubscription = this.categoryService.getCategoriesSummary()
      .pipe(
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe(categoriesSummary => {
        this.categoriesSummary = categoriesSummary;
      });
  }


  getAvatarColor(category: Category): string {
    let color = category.color;
    const hexColorRegex = /^(?:[0-9a-fA-F]{3}){1,2}$/;
    if (hexColorRegex.test(color)) {
      return `#${color}`;
    }

    return "#ddd";
  }

  onOpenCategory(category: Category): void {
    this.router.navigateByUrl(`/items?categoryId=${category.id}`,);
  }

}
