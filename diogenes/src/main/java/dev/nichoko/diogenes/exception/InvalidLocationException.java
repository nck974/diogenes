package dev.nichoko.diogenes.exception;

public class InvalidLocationException extends RuntimeException {

    public InvalidLocationException(int id) {
        super("Invalid location with id '" + id + "'.");
    }
}