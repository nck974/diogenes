package dev.nichoko.diogenes.model;

import dev.nichoko.diogenes.model.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategorySummary {
    private Category category;
    private int itemsNumber;

}
