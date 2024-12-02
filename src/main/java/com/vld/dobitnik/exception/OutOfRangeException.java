package com.vld.dobitnik.exception;


/**
 * Exception thrown when one of the numbers in the request is outside of the range permitted for the game
 *
 * @author: Vladimir Davidovic
 */
public class OutOfRangeException extends RuntimeException {
    public OutOfRangeException(String message) {
        super(message);
    }
}
