package com.vld.dobitnik.exception;

/**
 * Exception thrown when a mandatory field not present in the request.
 */
public class InvalidSchemaException extends RuntimeException {
    public InvalidSchemaException(String message) {
        super(message);
    }
}
