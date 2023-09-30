package dev.nichoko.diogenes.exception;

public class ImageCouldNotBeSavedException extends RuntimeException {

    public ImageCouldNotBeSavedException(String message) {
        super("The image could not be saved:" + message);
    }
}