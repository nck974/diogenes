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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
@NoArgsConstructor
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

    @Column(name = "updated_on")
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime updatedOn;

    @Column(name = "created_on")
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime createdOn;

    @Column(name = "image_path")
    @JsonProperty(access = Access.READ_ONLY)
    private String imagePath;

    @OneToOne()
    @JoinColumn(name = "category_id")
    @JsonProperty(access = Access.READ_ONLY)
    private Category category;

    @Transient
    private int categoryId;

    @OneToOne()
    @JoinColumn(name = "location_id")
    @JsonProperty(access = Access.READ_ONLY)
    private Location location;

    @Transient
    private int locationId;

    public Item(int id, String name, String description, int number) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.number = number;
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

    public void setCategory(Category category) {
        this.category = category;
        if (category != null) {
            this.categoryId = category.getId();
        }
    }

    public void setLocation(Location location) {
        this.location = location;
        if (location != null) {
            this.locationId = location.getId();
        }
    }

}