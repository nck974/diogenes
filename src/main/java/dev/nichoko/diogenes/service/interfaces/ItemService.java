package dev.nichoko.diogenes.service.interfaces;

import java.util.List;

import dev.nichoko.diogenes.model.dto.ItemDTO;

public interface ItemService {
    ItemDTO getItemById(Long id);

    List<ItemDTO> getAllItems();

    ItemDTO createItem(ItemDTO item);

    ItemDTO updateItem(Long id, ItemDTO item);

    void deleteItem(Long id);
}
