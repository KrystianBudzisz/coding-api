package com.masters.coding.common.exception;

public class LessonAlreadyStartedException extends RuntimeException {
    public LessonAlreadyStartedException(String message) {
        super(message);
    }
}
