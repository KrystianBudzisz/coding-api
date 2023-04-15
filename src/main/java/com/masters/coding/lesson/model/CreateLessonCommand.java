package com.masters.coding.lesson.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateLessonCommand { //todo check @NotNull or @Positive
    @Positive(message = "teacher required")
    private int teacherId;

    @Positive(message = "student required")
    private int studentId;

    @NotNull(message = "date required")
    @Future(message = "date has to be int the future")
    private LocalDateTime dateTime;

    public Lesson toEntity() {
        return Lesson.builder()
                .dateTime(dateTime)
                .active(true)
                .build();
    }

}
