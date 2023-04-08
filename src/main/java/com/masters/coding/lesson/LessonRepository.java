package com.masters.coding.lesson;

import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.model.Teacher;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer>{

    boolean existsByTeacherAndDateTimeBetween(Teacher teacher, LocalDateTime from, LocalDateTime to);

    List<Lesson> findLessonsByTeacher(Teacher teacher);

}
