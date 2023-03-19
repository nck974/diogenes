package dev.nichoko.diogenes.service;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.nichoko.diogenes.exception.ResourceNotFoundException;
import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.model.dto.ItemDTO;
import dev.nichoko.diogenes.service.interfaces.ItemService;
import dev.nichoko.diogenes.service.mapper.ItemMapper;
import dev.nichoko.diogenes.service.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

    private static final String ID_NOT_FOUND = "The following id could not be found: ";

    private final ItemRepository itemRepository;
    @Autowired
    @Qualifier("itemMapper")
    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    /*
     * Return a single item by id
     */
    @Override
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(ID_NOT_FOUND + id));
        return itemMapper.mapItemToItemDTO(item);
    }

    /*
     * Return all items
     */
    @Override
    public Page<ItemDTO> getAllItems(int pageSize, int offset) {
        Pageable pageable = PageRequest.of(offset, pageSize);
        Page<Item> itemsPage = itemRepository.findAll(pageable);
        return itemsPage.map(itemMapper::mapItemToItemDTO);

    }

    /*
     * Create a new item
     */
    @Override
    public ItemDTO createItem(ItemDTO itemDto) {
        Item item = itemMapper.mapItemDTOToItem(itemDto);
        Item savedItem = itemRepository.save(item);
        return itemMapper.mapItemToItemDTO(savedItem);
    }

    /*
     * Update an existing item or throw a not found exception
     */
    @Override
    public ItemDTO updateItem(Long id, ItemDTO itemDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(ID_NOT_FOUND + id));
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setNumber(itemDto.getNumber());
        Item savedItem = itemRepository.save(item);
        return itemMapper.mapItemToItemDTO(savedItem);
    }

    /*
     * Delete an existing item or throw a not found exception
     */
    @Override
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(ID_NOT_FOUND + id));
        itemRepository.delete(item);
    }

}
