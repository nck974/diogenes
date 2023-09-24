package dev.nichoko.diogenes.mock;

import dev.nichoko.diogenes.model.domain.Category;

public class CategoryMock {
        /*
     * Return a mock of an category
     */
    public static Category getMockCategory(Integer number) {
        return new Category(
                number,
                "TestName" + number.toString(),
                "Description" + number.toString(),
                "AB02" + number.toString());
    }

}
