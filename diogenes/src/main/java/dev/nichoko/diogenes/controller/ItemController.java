package dev.nichoko.diogenes.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import dev.nichoko.diogenes.model.ItemFilter;
import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.model.enums.SortDirection;
import dev.nichoko.diogenes.model.enums.SortingOption;
import dev.nichoko.diogenes.service.FileStorageService;
import dev.nichoko.diogenes.service.ItemService;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {
    private ItemService itemService;
    private FileStorageService fileStorageService;

    @Autowired
    public ItemController(ItemService itemService, FileStorageService fileStorageService) {
        this.itemService = itemService;
        this.fileStorageService = fileStorageService;
    }

    private ResponseEntity<Item> createNewItem(Item item, MultipartFile imageFile) {
        Item createdItem = itemService.createItem(item, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    private ResponseEntity<Item> updateItem(Integer id, Item item, MultipartFile imageFile) {
        Item updatedItem = itemService.updateItem(id, item, imageFile);
        return ResponseEntity.ok().body(updatedItem);
    }

    // CREATE
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> createItemWithoutImage(@Valid @RequestBody Item item) {
        return createNewItem(item, null);
    }

    @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Item> createItem(@Valid @RequestPart Item item,
            @RequestPart(name = "image", required = false) MultipartFile imageFile) {

        return createNewItem(item, imageFile);
    }

    // GET ALL
    @GetMapping("/")
    public ResponseEntity<Page<Item>> getAllItems(@RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "ID") SortingOption sort,
            @RequestParam(defaultValue = "ASC") SortDirection sortDirection,
            @ParameterObject @ModelAttribute ItemFilter filterOption) {
        return ResponseEntity
                .ok(itemService.getAllItemsPaged(pageSize, offset, sort.toString().toLowerCase(), sortDirection.toString(),
                        filterOption));
    }

    // GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@Valid @PathVariable int id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    // UPDATE
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> updateItem(@Valid @RequestBody Item updateItem, @PathVariable int id) {
        return updateItem(id, updateItem, null);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Item> updateItemWithImage(@Valid @RequestPart(name = "item") Item updateItem,
            @RequestPart(name = "image", required = false) MultipartFile imageFile, @PathVariable int id) {

        return updateItem(id, updateItem, imageFile);
    }

    // DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@Valid @PathVariable int id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/image")
    public ResponseEntity<Object> deleteItemImage(@Valid @PathVariable int id) {

        String imageFilename = itemService.deleteItemImage(id);
        fileStorageService.deleteItemImage(imageFilename);

        return ResponseEntity.noContent().build();
    }

}