package dev.nichoko.diogenes.service;

import org.springframework.data.domain.Page;

import dev.nichoko.diogenes.model.ItemFilter;
import dev.nichoko.diogenes.model.domain.Item;

public interface ItemService {
    Item getItemById(int id);
    Page<Item> getAllItems(int pageSize, int offset, String sort, String sortDirection, ItemFilter filter);
    Item createItem(Item item);
    Item updateItem(int id, Item item);
    void deleteItem(int id);
    String deleteItemImage(int id);
}
