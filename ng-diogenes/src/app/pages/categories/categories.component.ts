import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { Category } from 'src/app/models/Category';
import { CategoryService } from 'src/app/shared/services/category.service';

@Component({
    selector: 'app-categories',
    templateUrl: './categories.component.html',
    styleUrls: ['./categories.component.scss'],
    standalone: false
})
export class CategoriesComponent implements OnInit {

  categories: Category[] = [];
  isLoading: boolean = false;
  constructor(private categoryService: CategoryService, private router: Router) { }

  ngOnInit(): void {
    this.fetchCategories();
  }

  private fetchCategories() {
    this.isLoading = true;
    this.categoryService.getCategories()
      .pipe(finalize(() => this.isLoading = false))
      .subscribe((categories) => this.categories = categories);
  }

  onCreateNewCategory(): void {
    this.router.navigateByUrl("/categories/new");
  }
}
