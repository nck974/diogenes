package dev.nichoko.diogenes.exception;

public class InvalidCategoryException extends RuntimeException {

    public InvalidCategoryException(int id) {
        super("Invalid category with id '" + id + "'.");
    }
}