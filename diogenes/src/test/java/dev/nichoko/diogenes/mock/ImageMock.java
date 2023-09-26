package dev.nichoko.diogenes.mock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class ImageMock {
    public static MockMultipartFile getMockMultipartImage(String imagePath) throws IOException, FileNotFoundException {
        MockMultipartFile imagePart = new MockMultipartFile(
                "image",
                Paths.get(imagePath).getFileName().toString(),
                MediaType.IMAGE_JPEG_VALUE,
                new FileInputStream(imagePath));
        return imagePart;
    }
}
