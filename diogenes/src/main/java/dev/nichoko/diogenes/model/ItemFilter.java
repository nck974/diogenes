package dev.nichoko.diogenes.model;

public class ItemFilter {
    private String name;
    private String description;
    private Integer number;
    private Integer categoryId;

    public ItemFilter(String name, String description, Integer number, Integer categoryId) {
        this.name = name;
        this.description = description;
        this.number = number;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "ItemFilter [name=" + name + ", description=" + description + ", number=" + number + ", categoryId="
                + categoryId + "]";
    }

}
