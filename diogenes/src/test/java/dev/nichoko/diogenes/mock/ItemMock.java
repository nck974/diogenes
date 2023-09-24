package dev.nichoko.diogenes.mock;

import dev.nichoko.diogenes.model.domain.Category;
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
        item.setCategory(new Category(
                number,
                "name" + number,
                "description" + number,
                "col" + number));
        return item;
    }
}
