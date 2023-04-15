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
    private int studentId;
    private int teacherId;
    private LocalDateTime dateTime;

    public static LessonDto fromEntity(Lesson lesson) {
        return LessonDto.builder()
                .id(lesson.getId())
                .studentId(lesson.getStudent().getId())
                .teacherId(lesson.getTeacher().getId())
                .dateTime(lesson.getDateTime())
                .build();
    }
}
