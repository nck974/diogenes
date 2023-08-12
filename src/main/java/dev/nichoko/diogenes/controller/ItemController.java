package dev.nichoko.diogenes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import dev.nichoko.diogenes.enums.SortDirection;
import dev.nichoko.diogenes.model.dto.ItemDTO;
import dev.nichoko.diogenes.model.dto.ItemFilterDTO;
import dev.nichoko.diogenes.service.ItemService;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private ItemService itemService;

    private enum SortingOption {
        ID,
        NAME,
        DESCRIPTION,
        NUMBER,
    }

    @GetMapping("/")
    public ResponseEntity<Page<ItemDTO>> getAllItems(@RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "ID") SortingOption sort,
            @RequestParam(defaultValue = "ASC") SortDirection sortDirection,
            @ParameterObject @ModelAttribute  ItemFilterDTO filterOption) {
        logger.info("Received a request to retrieve all items. Offset: {} PageSize: {} Sort: {} SortDirection {} Filter {}",
                offset, pageSize,
                sort, sortDirection, filterOption);
        return ResponseEntity
                .ok(itemService.getAllItems(pageSize, offset, sort.toString().toLowerCase(), sortDirection.toString(),
                        filterOption));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@Valid @PathVariable Long id) {
        logger.info("Received a request to retrieve item with id: '{}'", id);
        ItemDTO item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@Valid @RequestBody ItemDTO updateItem, @PathVariable Long id) {
        logger.info("Received a request to update item with id: '{}'", id);
        ItemDTO updatedItem = itemService.updateItem(id, updateItem);
        return ResponseEntity.ok().body(updatedItem);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@Valid @PathVariable Long id) {
        logger.info("Received a request to delete item with id: '{}'", id);
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity<ItemDTO> createItem(@Valid @RequestBody ItemDTO item) {
        logger.info("Received a request to create new item: '{}'", item.getName());
        ItemDTO createdItem = itemService.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }
}
