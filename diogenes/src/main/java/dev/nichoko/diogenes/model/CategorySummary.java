package dev.nichoko.diogenes.model;

import dev.nichoko.diogenes.model.domain.Category;

public class CategorySummary {
    private Category category;
    private int itemsNumber;

    public CategorySummary(Category category, int itemsNumber) {
        this.category = category;
        this.itemsNumber = itemsNumber;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getItemsNumber() {
        return itemsNumber;
    }

    public void setItemsNumber(int itemsNumber) {
        this.itemsNumber = itemsNumber;
    }
}
