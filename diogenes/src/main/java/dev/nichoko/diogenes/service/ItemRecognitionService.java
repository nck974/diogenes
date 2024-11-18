package dev.nichoko.diogenes.service;

import org.springframework.web.multipart.MultipartFile;

import dev.nichoko.diogenes.model.RecognizedItem;

public interface ItemRecognitionService {
    RecognizedItem getItemInImage(MultipartFile imageFile);
}
