package dev.nichoko.diogenes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemFilter {
    private String name;
    private String description;
    private Integer number;
    private Integer categoryId;
    private Integer locationId;
}
