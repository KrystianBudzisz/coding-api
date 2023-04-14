package com.masters.coding.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleTeacherNotFoundException(TeacherNotFoundException exception) {
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(InvalidTeacherLanguageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleInvalidTeacherLanguageException(InvalidTeacherLanguageException exception) {
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleStudentNotFoundException(StudentNotFoundException exception) {
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(LanguageNotTaughtByTeacherException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleLanguageNotTaughtByTeacherException(LanguageNotTaughtByTeacherException exception) {
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(LessonInPastException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleLessonInPastException(LessonInPastException exception) {
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(LessonAlreadyStartedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleLessonAlreadyStartedException(LessonAlreadyStartedException exception) {
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(LessonConflictException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleLessonConflictException(LessonConflictException exception) {
        return new ExceptionDto(exception.getMessage());
    }

}
