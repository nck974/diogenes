package dev.nichoko.diogenes.service.interfaces;

import org.springframework.data.domain.Page;

import dev.nichoko.diogenes.model.dto.ItemDTO;
import dev.nichoko.diogenes.model.dto.ItemFilterDTO;

public interface ItemService {
    ItemDTO getItemById(Long id);

    Page<ItemDTO> getAllItems(int pageSize, int offset, String sort, String sortDirection, ItemFilterDTO filter);

    ItemDTO createItem(ItemDTO item);

    ItemDTO updateItem(Long id, ItemDTO item);

    void deleteItem(Long id);
}
