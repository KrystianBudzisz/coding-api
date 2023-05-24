package com.masters.coding.common.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ExceptionDto(exception.getMessage());
    }

//    @ExceptionHandler({
//            TeacherNotFoundException.class,
//            StudentNotFoundException.class,
//            LessonNotFoundException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ExceptionDto handleTeacherNotFoundException(RuntimeException exception) {
//        return new ExceptionDto(exception.getMessage());
//    }

    @ExceptionHandler(InvalidTeacherLanguageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleInvalidTeacherLanguageException(InvalidTeacherLanguageException exception) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ValidationErrorDto errorDto = new ValidationErrorDto();
        exception.getFieldErrors().forEach(error ->
                errorDto.addViolation(error.getField(), error.getDefaultMessage()));
        return errorDto;
    }

    @ExceptionHandler(TermNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleUpdatingLessonAndTermNotAvailableException(TermNotAvailableException exception) {
        return new ExceptionDto(exception.getMessage());
    }
}
