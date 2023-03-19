package dev.nichoko.diogenes.service.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.model.dto.ItemDTO;

@Service
public class ItemMapper {
    public Item mapItemDTOToItem(ItemDTO itemDTO) {
        Item item = new Item();
        BeanUtils.copyProperties(itemDTO, item);
        return item;
    }

    public ItemDTO mapItemToItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(item, itemDTO);
        return itemDTO;
    }
}
