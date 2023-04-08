package com.masters.coding.lesson.model;

import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.model.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;

    private LocalDateTime dateTime;

    public String toString() {
        return student.getFirstName() + " " + student.getLastName();
    }
}
