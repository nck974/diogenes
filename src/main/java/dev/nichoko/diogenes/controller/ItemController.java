package dev.nichoko.diogenes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.nichoko.diogenes.exception.ResourceNotFoundException;
import dev.nichoko.diogenes.model.dto.ItemDTO;
import dev.nichoko.diogenes.service.interfaces.ItemService;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        logger.info("Received a request to: {}", id);
        ItemDTO item = itemService.getItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        }
        throw new ResourceNotFoundException("The item with id " + id.toString() + " could not be found");
    }
}
