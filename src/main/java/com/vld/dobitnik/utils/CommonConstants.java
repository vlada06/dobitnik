package com.vld.dobitnik.utils;

public class CommonConstants {

    public static final String EMPTY_JSON_ERROR = "The request cannot be empty or null";
    public static final String POSITIVE_NUMBERS_EXCEPTION_MSG = "numbers %n or %n have to be greater than zero";

    public CommonConstants() {
        throw new IllegalStateException("This class should not be instantiated as it's for static constants only");

    }
}
