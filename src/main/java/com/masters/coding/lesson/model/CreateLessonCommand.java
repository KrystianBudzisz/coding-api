package com.masters.coding.lesson.model;

import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.model.Teacher;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateLessonCommand {//todo check @NotNull or @Positive
    @NotNull(message = "teacher required")
    private Teacher teacher;

    @NotNull(message = "student required")
    private Student student;

    @NotNull(message = "language required")
    private LocalDateTime dateTime;

    public Lesson toEntity() {
        return Lesson.builder()
                .teacher(teacher)
                .student(student)
                .dateTime(dateTime)
                .active(true)
                .build();
    }

}
