package org.acme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FruitsExceptions {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {

        public NotFoundException(String message) {
            super(message);
        }

    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class UnprocessableEntityException extends RuntimeException {

        public UnprocessableEntityException(String message) {
            super(message);
        }

    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public static class UnsupportedMediaTypeException extends RuntimeException {

        public UnsupportedMediaTypeException(String message) {
            super(message);
        }

    }
}
