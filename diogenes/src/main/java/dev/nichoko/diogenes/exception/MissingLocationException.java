package dev.nichoko.diogenes.exception;

public class MissingLocationException extends RuntimeException {

    public MissingLocationException() {
        super("An item needs to have a location assigned.");
    }
}