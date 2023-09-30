package dev.nichoko.diogenes.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveItemImage(MultipartFile imageFile);
    Resource readItemImage(String filename);
    void deleteItemImage(String filename);
}
