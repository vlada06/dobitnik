package com.vld.dobitnik.exception;

/**
 * Thrown if combination pool is greater than or equal to combination size,
 * or if either combination pool or combination size are null, negative or zero.
 * <p>
 * @author Vladimir Davidovic
 * date: 15/12/2019
 * time: 11:06
 */

public class CombinationSizeException extends RuntimeException {
    public CombinationSizeException(String message) {
        super(message);
    }
}
