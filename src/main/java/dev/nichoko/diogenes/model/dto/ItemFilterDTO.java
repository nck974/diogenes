package dev.nichoko.diogenes.model.dto;

public class ItemFilterDTO {
    private String name;
    private String description;
    private Integer number;

    public ItemFilterDTO(String name, String description, Integer number) {
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

    /*
     * Show a meaningful message on what does the filter do
     */
    @Override
    public String toString() {
        String string = "";
        if (name != null && !name.isBlank()) {
            string += " name: " + name;
        }
        if (description != null && !description.isBlank()) {
            string += " description: " + description;
        }

        if (number != null) {
            string += " number: " + number.toString();
        }

        if (!string.isBlank()) {
            return string.strip();
        }

        return "None";
    }
}
