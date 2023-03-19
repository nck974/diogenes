package dev.nichoko.diogenes.service.interfaces;

import org.springframework.data.domain.Page;

import dev.nichoko.diogenes.model.dto.ItemDTO;

public interface ItemService {
    ItemDTO getItemById(Long id);

    Page<ItemDTO> getAllItems(int pageSize, int offset);

    ItemDTO createItem(ItemDTO item);

    ItemDTO updateItem(Long id, ItemDTO item);

    void deleteItem(Long id);
}
