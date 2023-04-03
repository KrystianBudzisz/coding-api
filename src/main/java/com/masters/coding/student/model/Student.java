package com.masters.coding.student.model;

import com.masters.coding.common.Language;
import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.teacher.model.Teacher;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@SQLDelete(sql = "UPDATE student SET active = '0' WHERE id = ?")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne
    private Teacher teacher;

    private boolean active;

    @OneToMany(mappedBy = "student")
    private Set<Lesson> lessonList;
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
