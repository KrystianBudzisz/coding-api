package com.masters.coding.common.exception;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException (String message) {
        super(message);
    }
}
