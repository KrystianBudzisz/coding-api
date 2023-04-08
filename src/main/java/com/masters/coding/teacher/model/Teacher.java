package com.masters.coding.teacher.model;

import com.masters.coding.common.Language;
import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.student.model.Student;
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
@SQLDelete(sql = "UPDATE teacher SET active = '0' WHERE id = ?")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "teacher_language", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "language")
    private Set<Language> languages;

    @OneToMany(mappedBy = "teacher")
    private Set<Student> students;


    @OneToMany(mappedBy = "teacher")
    private Set<Lesson> lessonList;

    boolean active;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
