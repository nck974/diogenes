package dev.nichoko.diogenes.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dev.nichoko.diogenes.config.FileStorageConfig;

@Repository
public class FileSystemRepositoryImpl implements FileSystemRepository {

    private FileStorageConfig fileStorageConfig;

    @Autowired
    public FileSystemRepositoryImpl(FileStorageConfig fileStorageConfig) {
        this.fileStorageConfig = fileStorageConfig;
    }

    @Override
    public String save(byte[] content, String fileName, String folder) throws IOException {
        Path newFile = Paths.get(fileStorageConfig.getUploadDir(), folder, new Date().getTime() + "-" + fileName);
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile.toString();
    }
}
