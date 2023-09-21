package dev.nichoko.diogenes.repository;

import java.io.IOException;

public interface FileSystemRepository {

    public String save(byte[] content, String fileName, String folder) throws IOException;
}
