package dev.nichoko.diogenes.service;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.nichoko.diogenes.exception.ImageCouldNotBeSavedException;
import dev.nichoko.diogenes.exception.UnsupportedImageFormatException;
import dev.nichoko.diogenes.repository.FileSystemRepository;
import dev.nichoko.diogenes.utils.FileNameCleaner;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String ITEMS_FOLDER = "item";
    private static final String ITEM_IMAGES_FOLDER = "image";

    private static final String[] ALLOWED_IMAGE_CONTENT_TYPES = {
            "image/jpeg",
            "image/png",
            "image/gif",
    };

    private FileSystemRepository fileSystemRepository;

    @Autowired
    FileStorageServiceImpl(FileSystemRepository fileSystemRepository) {
        this.fileSystemRepository = fileSystemRepository;
    }

    private boolean isImage(MultipartFile imageFile) {
        String contentType = imageFile.getContentType();

        for (String allowedContentType : ALLOWED_IMAGE_CONTENT_TYPES) {
            if (allowedContentType.equals(contentType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String saveItemImage(MultipartFile imageFile) {
        try {

            if (!isImage(imageFile)) {
                throw new UnsupportedImageFormatException();
            }

            String imagesFolder = Paths.get(ITEMS_FOLDER, ITEM_IMAGES_FOLDER).toString();
            String filename = FileNameCleaner.cleanFileName(imageFile.getOriginalFilename());

            return fileSystemRepository.save(imageFile.getBytes(), filename, imagesFolder);

        } catch (IOException exception) {
            throw new ImageCouldNotBeSavedException(exception.getMessage());
        }
    }

}
