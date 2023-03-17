package dev.nichoko.diogenes.service.mapper;

import org.springframework.stereotype.Service;

import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.model.dto.ItemDTO;

@Service
public class ItemMapper {
    public Item mapItemDTOToItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setNumber(itemDTO.getNumber());

        return item;
    }

    public ItemDTO mapItemToItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setNumber(item.getNumber());

        return itemDTO;
    }
}
