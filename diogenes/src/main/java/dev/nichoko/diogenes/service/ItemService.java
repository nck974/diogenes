package dev.nichoko.diogenes.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import dev.nichoko.diogenes.model.ItemFilter;
import dev.nichoko.diogenes.model.domain.Item;

public interface ItemService {
    Item getItemById(int id);
    Page<Item> getAllItems(int pageSize, int offset, String sort, String sortDirection, ItemFilter filter);
    Item createItem(Item item, MultipartFile imageFile);
    Item updateItem(int id, Item item, MultipartFile imageFile);
    void deleteItem(int id);
    String deleteItemImage(int id);
}
