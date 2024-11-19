package dev.nichoko.diogenes.mock;

import dev.nichoko.diogenes.model.RecognizedItem;

public class RecognizedItemMock {

    public static RecognizedItem getRecognizedItemMock() {
        RecognizedItem recognizedImage = new RecognizedItem();
        recognizedImage.setItem("This is a test item");
        recognizedImage.setDescription("A hardcoded description for the image");
        return recognizedImage;
    }

}
