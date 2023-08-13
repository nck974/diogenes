package dev.nichoko.diogenes.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = Access.READ_ONLY)
    private int id;

    @Column(nullable = false)
    @Schema(description = "The name of the item", nullable = false)
    @NotEmpty
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

    @Column(nullable = false)
    @Schema(description = "Description of the item")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Column(nullable = false)
    @Schema(description = "Number of items", nullable = false)
    @Min(value = 0, message = "Number must be greater than or equal to 0")
    @Max(value = 100000000, message = "Number must be less than or equal to 100000000")
    private int number;

    public Item() {
    }

    public Item(int id, String name, String description, int number) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", name=" + name + ", description=" + description + ", number=" + number + "]";
    }

}