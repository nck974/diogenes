package dev.nichoko.diogenes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {

    private String uploadsDir;
    private String imagesDir;
    private String imagesBasePath;

    public String getImagesBasePath() {
        return imagesBasePath;
    }

    public void setImagesBasePath(String imagesBasePath) {
        this.imagesBasePath = imagesBasePath;
    }

    public String getImagesDir() {
        return imagesDir;
    }

    public void setImagesDir(String imagesDir) {
        this.imagesDir = imagesDir;
    }

    public String getUploadsDir() {
        return uploadsDir;
    }

    public void setUploadsDir(String uploadsDir) {
        this.uploadsDir = uploadsDir;
    }
}
