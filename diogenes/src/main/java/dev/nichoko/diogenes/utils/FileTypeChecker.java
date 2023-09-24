package dev.nichoko.diogenes.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class FileTypeChecker {
    private static final Map<String, MediaType> extensionToMediaType;
    static {
        Map<String, MediaType> map = new HashMap<>();
        map.put("jpg", MediaType.IMAGE_JPEG);
        map.put("jpeg", MediaType.IMAGE_JPEG);
        map.put("png", MediaType.IMAGE_PNG);
        map.put("gif", MediaType.IMAGE_GIF);
        extensionToMediaType = Collections.unmodifiableMap(map);
    }

    private FileTypeChecker() {
    }

    private static String getFileExtension(String imageName) {
        int dotIndex = imageName.lastIndexOf('.');
        return (dotIndex > 0) ? imageName.substring(dotIndex + 1).toLowerCase() : "";
    }

    /**
     * Use the file extension to try to guess out of the accepted image types the
     * content type
     * 
     * @param imageName
     * @return
     */
    public static MediaType guessImageContentType(String imageName) {
        String fileExtension = getFileExtension(imageName);

        return FileTypeChecker.extensionToMediaType.getOrDefault(fileExtension.toLowerCase(),
                MediaType.APPLICATION_OCTET_STREAM);
    }

}
