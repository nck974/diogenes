package dev.nichoko.diogenes.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final String ID_NOT_FOUND = "The following id could not be found: ";

    public ResourceNotFoundException(int id) {
        super(ID_NOT_FOUND + id);
    }
}