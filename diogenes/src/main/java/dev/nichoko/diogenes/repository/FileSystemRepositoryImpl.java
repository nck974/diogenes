package dev.nichoko.diogenes.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import dev.nichoko.diogenes.config.FileStorageConfig;
import dev.nichoko.diogenes.exception.FileNotReadableException;
import dev.nichoko.diogenes.exception.ResourceNotFoundException;

@Repository
public class FileSystemRepositoryImpl implements FileSystemRepository {

    private FileStorageConfig fileStorageConfig;

    @Autowired
    public FileSystemRepositoryImpl(FileStorageConfig fileStorageConfig) {
        this.fileStorageConfig = fileStorageConfig;
    }

    @Override
    public String save(byte[] content, String filename, String folder) throws IOException {
        Path newFile = Paths.get(fileStorageConfig.getUploadsDir(), folder, new Date().getTime() + "-" + filename);
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile.toString();
    }

    @Override
    public Resource read(String filename, String folder) throws IOException {
        Path filePath = Paths.get(fileStorageConfig.getUploadsDir(), folder, filename);

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new ResourceNotFoundException("The file " + filename + " could not be found");
        }
        if (!resource.isReadable()) {
            throw new FileNotReadableException("The file " + filename + " could not be read");
        }
        return resource;
    }

}
