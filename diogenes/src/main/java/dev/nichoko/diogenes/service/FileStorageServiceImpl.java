package dev.nichoko.diogenes.service;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.nichoko.diogenes.config.FileStorageConfig;
import dev.nichoko.diogenes.exception.ErrorReadingImageException;
import dev.nichoko.diogenes.exception.FileNotReadableException;
import dev.nichoko.diogenes.exception.ImageCouldNotBeSavedException;
import dev.nichoko.diogenes.exception.ResourceNotFoundException;
import dev.nichoko.diogenes.exception.UnsupportedImageFormatException;
import dev.nichoko.diogenes.repository.FileSystemRepository;
import dev.nichoko.diogenes.utils.FileNameCleaner;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String[] ALLOWED_IMAGE_CONTENT_TYPES = {
            "image/jpeg",
            "image/png",
            "image/gif",
    };

    private FileSystemRepository fileSystemRepository;
    private FileStorageConfig fileStorageConfig;

    @Autowired
    FileStorageServiceImpl(FileSystemRepository fileSystemRepository, FileStorageConfig fileStorageConfig) {
        this.fileSystemRepository = fileSystemRepository;
        this.fileStorageConfig = fileStorageConfig;
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
    public Resource readItemImage(String filename) {
        try {
            Resource image = fileSystemRepository.read(filename, fileStorageConfig.getImagesDir());
            if (!image.exists()) {
                throw new ResourceNotFoundException("The image " + filename + " could not be found");
            }

            if (!image.isReadable()) {
                throw new FileNotReadableException("The image " + filename + "can not be read");
            }

            return image;
        } catch (IOException exception) {
            throw new ErrorReadingImageException(exception.getMessage());
        }

    }

    @Override
    public String saveItemImage(MultipartFile imageFile) {
        try {

            if (!isImage(imageFile)) {
                throw new UnsupportedImageFormatException();
            }

            String filename = FileNameCleaner.cleanFileName(imageFile.getOriginalFilename());

            String filePath = fileSystemRepository.save(imageFile.getBytes(), filename,
                    fileStorageConfig.getImagesDir());

            return Paths.get(filePath).getFileName().toString();
        } catch (IOException exception) {
            throw new ImageCouldNotBeSavedException(exception.getMessage());
        }
    }

}
