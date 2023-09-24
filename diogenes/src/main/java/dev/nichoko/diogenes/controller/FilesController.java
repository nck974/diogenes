package dev.nichoko.diogenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.nichoko.diogenes.service.FileStorageService;
import dev.nichoko.diogenes.utils.FileTypeChecker;

@RestController
@RequestMapping("/api/v1/files")
public class FilesController {

    private FileStorageService fileStorageService;

    @Autowired
    public FilesController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> serveImage(@PathVariable String imageName) {
        Resource resource = fileStorageService.readItemImage(imageName);
        return ResponseEntity.ok()
                .contentType(FileTypeChecker.guessImageContentType(imageName))
                .body(resource);

    }
}