package dev.nichoko.diogenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.nichoko.diogenes.exception.ResourceNotFoundException;
import dev.nichoko.diogenes.model.dto.ItemDTO;
import dev.nichoko.diogenes.service.interfaces.ItemServiceInterface;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    @Autowired
    private ItemServiceInterface itemService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        System.out.println("Received a request to: " + id.toString());
        ItemDTO item = itemService.getItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        }
        throw new ResourceNotFoundException("The item with id " + id.toString() + " could not be found");
    }
}
