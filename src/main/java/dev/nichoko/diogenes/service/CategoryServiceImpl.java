package dev.nichoko.diogenes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.nichoko.diogenes.exception.NameAlreadyExistsException;
import dev.nichoko.diogenes.exception.ResourceNotFoundException;
import dev.nichoko.diogenes.model.domain.Category;
import dev.nichoko.diogenes.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(id));

    }

    /**
     * Check if another item with the same name already exists in the database
     * 
     * @param category
     */
    private void validateName(Category category) {
        String categoryName = category.getName();
        if (categoryRepository.existsByName(categoryName)) {
            throw new NameAlreadyExistsException(
                    "Category with the name " + categoryName + " already exists.");
        }
    }

    /*
     * Return all categories
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /*
     * Create a new category
     */
    @Override
    public Category createCategory(Category category) {
        validateName(category);

        return categoryRepository.save(category);
    }

    /*
     * Update an existing category or throw a not found exception
     */
    @Override
    public Category updateCategory(int id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    category.setId(existingCategory.getId());
                    category.setCreatedOn(existingCategory.getCreatedOn());
                    if (!category.getName().equals(existingCategory.getName())) {
                        validateName(category);
                    }
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /*
     * Delete an existing category or throw a not found exception
     */
    @Override
    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(id));
        categoryRepository.delete(category);
    }

}
