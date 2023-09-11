package dev.nichoko.diogenes.model;

public class ItemFilter {
    private String name;
    private String description;
    private Integer number;

    public ItemFilter(String name, String description, Integer number) {
        this.name = name;
        this.description = description;
        this.number = number;
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

    @Override
    public String toString() {
        return "ItemFilter [name=" + name + ", description=" + description + ", number=" + number + "]";
    }

}