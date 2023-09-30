package dev.nichoko.diogenes.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.nichoko.diogenes.exception.ImageCouldNotBeSavedException;
import dev.nichoko.diogenes.exception.InvalidCategoryException;
import dev.nichoko.diogenes.exception.MissingCategoryException;
import dev.nichoko.diogenes.exception.NameAlreadyExistsException;
import dev.nichoko.diogenes.exception.ResourceNotFoundException;
import dev.nichoko.diogenes.exception.UnsupportedImageFormatException;

@RestControllerAdvice
public class ResourceNotFoundAdviceController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(ResourceNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleNameAlreadyExistsException(NameAlreadyExistsException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(MissingCategoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingCategory(MissingCategoryException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidCategoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidCategory(InvalidCategoryException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ImageCouldNotBeSavedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleImageCouldNotBeSaved(ImageCouldNotBeSavedException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UnsupportedImageFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidImageFormat(UnsupportedImageFormatException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}