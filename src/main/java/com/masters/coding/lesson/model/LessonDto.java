package com.masters.coding.lesson.model;

import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.model.Teacher;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LessonDto {
    private int id;
    private Student student;
    private Teacher teacher;
    private LocalDateTime dateTime;

    public static LessonDto fromEntity(Lesson lesson) {
        return LessonDto.builder()
                .id(lesson.getId())
                .student(lesson.getStudent())
                .teacher(lesson.getTeacher())
                .dateTime(lesson.getDateTime())
                .build();
    }
}
