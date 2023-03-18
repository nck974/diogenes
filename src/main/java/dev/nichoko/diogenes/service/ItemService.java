package dev.nichoko.diogenes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.model.dto.ItemDTO;
import dev.nichoko.diogenes.service.interfaces.ItemServiceInterface;
import dev.nichoko.diogenes.service.mapper.ItemMapper;
import dev.nichoko.diogenes.service.repository.ItemRepositoryInterface;

@Service
public class ItemService implements ItemServiceInterface {

    private final ItemRepositoryInterface itemRepository;
    @Autowired
    @Qualifier("itemMapper")
    private final ItemMapper itemMapper;

    public ItemService(ItemRepositoryInterface itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDTO getItemById(Long i) {
        Optional<Item> item = itemRepository.findById(i);
        if (item.isPresent()){
            return itemMapper.mapItemToItemDTO(item.get());
        }
        return null;
    }

    @Override
    public List<ItemDTO> getAllItems() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllItems'");
    }

    @Override
    public ItemDTO createItem(ItemDTO ItemDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createItem'");
    }

    @Override
    public ItemDTO updateItem(Long id, ItemDTO ItemDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateItem'");
    }

    @Override
    public void deleteItem(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteItem'");
    }
    
}
