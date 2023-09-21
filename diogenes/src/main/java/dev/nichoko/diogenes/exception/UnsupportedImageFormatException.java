package dev.nichoko.diogenes.exception;

public class UnsupportedImageFormatException extends RuntimeException {

    public UnsupportedImageFormatException() {
        super("Only jpg, gif and png images are supported");
    }
}