package dev.nichoko.diogenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.nichoko.diogenes.model.RecognizedItem;
import dev.nichoko.diogenes.service.ItemRecognitionService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/ai")
public class AiController {

    private ItemRecognitionService itemRecognitionService;

    @Autowired
    public AiController(ItemRecognitionService itemRecognitionService) {
        this.itemRecognitionService = itemRecognitionService;
    }

    @PostMapping(path = "/recognize-item", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecognizedItem> recognizeImage(
            @RequestPart(name = "image", required = true) MultipartFile imageFile) {
        return ResponseEntity
                .ok(itemRecognitionService.getItemInImage(imageFile));
    }
}
