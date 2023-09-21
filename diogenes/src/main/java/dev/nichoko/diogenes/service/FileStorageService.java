package dev.nichoko.diogenes.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveItemImage(MultipartFile imageFile);
}
