package dev.nichoko.diogenes.mock;

import dev.nichoko.diogenes.model.domain.Item;

public class ItemMock {

    /*
     * Return a mock of an item
     */
    public static Item getMockItem(Integer number) {
        Item item = new Item(
                number,
                "TestName" + number.toString(),
                "Description" + number.toString(),
                number);

        item.setCategory(CategoryMock.getMockCategory(number));
        item.setLocation(LocationMock.getMockLocation(number));

        return item;
    }
}
