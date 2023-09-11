package dev.nichoko.diogenes.model.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = Access.READ_ONLY)
    private int id;

    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the category", nullable = false)
    @NotEmpty
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

    @Column(nullable = false)
    @Schema(description = "Description of the item")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Column(nullable = false)
    @Schema(description = "Color of the item as just the HEX number without #")
    @Size(max = 6, message = "Color cannot exceed 6 characters")
    private String color;

    @Column(name = "updated_on")
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime updatedOn;

    @Column(name = "created_on")
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime createdOn;

    public Category(int id,
            String name,
            String description,
            String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public Category(
            String name,
            String description,
            String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public Category() {
    }

    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
        updatedOn = createdOn;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedOn = LocalDateTime.now();
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", description=" + description + ", color=" + color
                + ", updatedOn=" + updatedOn + ", createdOn=" + createdOn + "]";
    }

}
