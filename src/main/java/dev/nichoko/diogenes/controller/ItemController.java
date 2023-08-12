package dev.nichoko.diogenes.controller;

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
import dev.nichoko.diogenes.enums.SortingOption;
import dev.nichoko.diogenes.model.ItemFilter;
import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.service.ItemService;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Item>> getAllItems(@RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "ID") SortingOption sort,
            @RequestParam(defaultValue = "ASC") SortDirection sortDirection,
            @ParameterObject @ModelAttribute ItemFilter filterOption) {
        return ResponseEntity
                .ok(itemService.getAllItems(pageSize, offset, sort.toString().toLowerCase(), sortDirection.toString(),
                        filterOption));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@Valid @PathVariable int id) {
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@Valid @RequestBody Item updateItem, @PathVariable int id) {
        Item updatedItem = itemService.updateItem(id, updateItem);
        return ResponseEntity.ok().body(updatedItem);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@Valid @PathVariable int id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        Item createdItem = itemService.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }
}
