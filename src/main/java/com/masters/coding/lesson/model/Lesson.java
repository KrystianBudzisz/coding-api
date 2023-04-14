package com.masters.coding.lesson.model;

import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.model.Teacher;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@SQLDelete(sql = "UPDATE lesson SET active = '0' WHERE id = ?")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;

    private LocalDateTime dateTime;

    private boolean active;

    public String toString() { //TODO  Check changes
        return "Student: " + student.getFirstName() + " " + student.getLastName() + "Teacher: " +
                teacher.getFirstName() + " " + teacher.getLastName();
    }
}
