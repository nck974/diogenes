package dev.nichoko.diogenes.exception;

public class MissingCategoryException extends RuntimeException {

    public MissingCategoryException() {
        super("An item needs to have a category assigned.");
    }
}