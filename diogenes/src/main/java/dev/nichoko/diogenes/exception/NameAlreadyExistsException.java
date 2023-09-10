package dev.nichoko.diogenes.exception;

public class NameAlreadyExistsException extends RuntimeException {

    public NameAlreadyExistsException(String message) {
        super(message);
    }
}