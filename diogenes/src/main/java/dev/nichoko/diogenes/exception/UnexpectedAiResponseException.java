package dev.nichoko.diogenes.exception;

public class UnexpectedAiResponseException extends RuntimeException {

    public UnexpectedAiResponseException(String message) {
        super(message);
    }
}