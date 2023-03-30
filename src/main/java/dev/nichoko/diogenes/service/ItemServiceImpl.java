package dev.nichoko.diogenes.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.nichoko.diogenes.enums.SortDirection;
import dev.nichoko.diogenes.exception.ResourceNotFoundException;
import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.model.dto.ItemDTO;
import dev.nichoko.diogenes.model.dto.ItemFilterDTO;
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
     * If the filters are available filter by each field. Strings match everything
     * lower case
     * number match the exact number
     */
    private Specification<Item> filterItems(ItemFilterDTO filter) {
        Specification<Item> spec = Specification.where(null);
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")),
                    "%" + filter.getName().toLowerCase() + "%"));
        }

        if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
            spec = spec.and(
                    (root, query, cb) -> cb.like(cb.lower(root.get("description")),
                            "%" + filter.getDescription().toLowerCase() + "%"));
        }

        if (filter.getNumber() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("number"), filter.getNumber()));
        }
        return spec;
    }

    /*
     * Return all items
     */
    @Override
    public Page<ItemDTO> getAllItems(int pageSize, int offset, String sort, String sortDirection,
            ItemFilterDTO filter) {

        // Sort
        Sort sorting = Sort.by(sort);
        if (sortDirection.equals(SortDirection.DESC.toString())) {
            sorting = sorting.descending();
        }

        // Filter
        Specification<Item> spec = filterItems(filter);

        // Query pageable
        Pageable pageable = PageRequest.of(offset, pageSize, sorting);
        Page<Item> itemsPage = itemRepository.findAll(spec, pageable);
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
