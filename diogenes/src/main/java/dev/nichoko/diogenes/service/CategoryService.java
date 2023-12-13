package dev.nichoko.diogenes.service;

import java.util.List;

import dev.nichoko.diogenes.model.CategorySummary;
import dev.nichoko.diogenes.model.domain.Category;

public interface CategoryService {
    Category getCategoryById(int id);

    List<Category> getAllCategories();

    List<CategorySummary> getCategoriesSummary();

    Category createCategory(Category item);

    Category updateCategory(int id, Category item);

    void deleteCategory(int id);
}
