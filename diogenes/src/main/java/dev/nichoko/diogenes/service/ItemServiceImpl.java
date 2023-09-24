package dev.nichoko.diogenes.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.nichoko.diogenes.exception.MissingCategoryException;
import dev.nichoko.diogenes.exception.ResourceNotFoundException;
import dev.nichoko.diogenes.config.FileStorageConfig;
import dev.nichoko.diogenes.exception.InvalidCategoryException;
import dev.nichoko.diogenes.model.ItemFilter;
import dev.nichoko.diogenes.model.domain.Category;
import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.model.enums.SortDirection;
import dev.nichoko.diogenes.repository.CategoryRepository;
import dev.nichoko.diogenes.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageConfig fileStorageConfig;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository,
            FileStorageConfig fileStorageConfig) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.fileStorageConfig = fileStorageConfig;
    }

    /**
     * Add the url where the item can be retrieved to the provided item
     * 
     * @param item
     */
    private void setImageBasePath(Item item) {
        if (item.getImagePath() != null) {
            item.setImagePath(fileStorageConfig.getImagesBasePath() + item.getImagePath());
        }
    }

    /**
     * For some reason the categoryId does not seem to be automatically filled by
     * JPA
     * 
     * @param item
     */
    private void setCategoryId(Item item) {
        if (item.getCategory() != null) {
            item.setCategoryId(item.getCategory().getId());
        }
    }

    /**
     * Set fields that will be returned to the user and are not stored in the
     * database as such
     * 
     * @param createdItem
     */
    private void setTransientFields(Item createdItem) {
        setImageBasePath(createdItem);
        setCategoryId(createdItem);
    }

    /*
     * Return a single item by id
     */
    @Override
    public Item getItemById(int id) {
        return itemRepository.findById(id).map(itemFound -> {
            setTransientFields(itemFound);
            return itemFound;
        })
                .orElseThrow(
                        () -> new ResourceNotFoundException(id));
    }

    /*
     * If the filters are available filter by each field. Strings match everything
     * lower case
     * number match the exact number
     */
    private Specification<Item> filterItems(ItemFilter filter) {
        Specification<Item> spec = Specification.where(null);

        // Filter by name
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")),
                    "%" + filter.getName().toLowerCase() + "%"));
        }

        // Filter by description
        if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
            spec = spec.and(
                    (root, query, cb) -> cb.like(cb.lower(root.get("description")),
                            "%" + filter.getDescription().toLowerCase() + "%"));
        }

        // Filter by number
        if (filter.getNumber() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("number"), filter.getNumber()));
        }

        // Filter by category
        if (filter.getCategoryId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category").get("id"), filter.getCategoryId()));
        }

        return spec;
    }

    /*
     * Return all items
     */
    @Override
    public Page<Item> getAllItems(int pageSize, int offset, String sort, String sortDirection,
            ItemFilter filter) {

        // Sort
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection.equals(SortDirection.DESC.toString())) {
            direction = Sort.Direction.DESC;
        }

        Sort.Order order = new Sort.Order(direction, sort).ignoreCase();
        Sort sorting = Sort.by(order);

        // Filter
        Specification<Item> spec = filterItems(filter);

        // Query pageable
        Pageable pageable = PageRequest.of(offset, pageSize, sorting);
        return itemRepository.findAll(spec, pageable).map(existingItem -> {
            setTransientFields(existingItem);
            return existingItem;
        });
    }

    /**
     * Checks that the provided category exists and returns it
     * 
     * @param item
     * @return
     * @throws MissingCategoryException
     * @throws InvalidCategoryException
     */
    private Category findCategory(Item item) throws MissingCategoryException, InvalidCategoryException {
        final int categoryId = item.getCategoryId();

        if (categoryId == 0) {
            throw new MissingCategoryException();
        }

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new InvalidCategoryException(categoryId));
    }

    /*
     * Create a new item
     */
    @Override
    public Item createItem(Item item) {

        item.setCategory(this.findCategory(item));

        Item createdItem = itemRepository.save(item);
        setTransientFields(createdItem);
        return createdItem;
    }

    /*
     * Update an existing item or throw a not found exception
     */
    @Override
    public Item updateItem(int id, Item item) {
        return itemRepository.findById(id)
                .map(existingItem -> {
                    // Set fields that shall not be changed
                    item.setId(existingItem.getId());
                    item.setCategory(this.findCategory(item));
                    item.setCreatedOn(existingItem.getCreatedOn());
                    item.setImagePath(existingItem.getImagePath());

                    Item updatedItem = itemRepository.save(item);
                    setTransientFields(updatedItem);
                    return updatedItem;
                })
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /*
     * Delete an existing item or throw a not found exception
     */
    @Override
    public void deleteItem(int id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(id));
        itemRepository.delete(item);
    }

}
