package dev.nichoko.diogenes.service.interfaces;

import java.util.List;

import dev.nichoko.diogenes.model.dto.ItemDTO;

public interface ItemServiceInterface {
    ItemDTO getItemById(Long id);

    List<ItemDTO> getAllItems();

    ItemDTO createItem(ItemDTO ItemDTO);

    ItemDTO updateItem(Long id, ItemDTO ItemDTO);

    void deleteItem(Long id);
}
