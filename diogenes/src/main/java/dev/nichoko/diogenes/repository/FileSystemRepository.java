package dev.nichoko.diogenes.repository;

import java.io.IOException;

import org.springframework.core.io.Resource;

public interface FileSystemRepository {

    public String save(byte[] content, String fileName, String folder) throws IOException;
    public Resource read(String fileName, String folder) throws IOException;
}
