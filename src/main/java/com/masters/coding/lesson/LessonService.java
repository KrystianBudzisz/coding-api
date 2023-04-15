package com.masters.coding.lesson;

import com.masters.coding.common.exception.*;
import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.student.StudentRepository;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.TeacherRepository;
import com.masters.coding.teacher.model.Teacher;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public Lesson findById(int id) {
        throw new RuntimeException();
//        return lessonRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException(Lesson.class.getSimpleName(), id));
    }

    public Lesson save(Lesson lesson, int studentId, int teacherId) {
        LocalDateTime newTime = lesson.getDateTime();
        if (newTime.isBefore(LocalDateTime.now())) {
            throw new LessonInPastException("Lekcja nie moze byc tworzona w przeszlosci");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(Student.class.getSimpleName(), studentId));
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(Teacher.class.getSimpleName(), teacherId));


        if (lessonRepository.existsByTeacherAndDateTimeBetween(teacher, newTime.minusMinutes(59),
                newTime.plusMinutes(59))) {
            throw new LessonConflictException("Inna lekcja zostala juz rozpoczeta w tym czasie");
        }

        lesson.setStudent(student);
        lesson.setTeacher(teacher);

        lessonRepository.save(lesson);
        return lesson;
    }

    public Lesson updateLessonTime(int id, LocalDateTime newTime) {
        if (newTime.isBefore(LocalDateTime.now())) {
            throw new LessonInPastException("Lekcja nie moze zostac stworzona w przeszlosci");
        }
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Lesson.class.getSimpleName(), id));

        if (lesson.getDateTime().isBefore(LocalDateTime.now())) {
            throw new LessonAlreadyStartedException("Lekcja juz sie rozpoczela");
        }
        if (lessonRepository.existsByTeacherAndDateTimeBetween(
                lesson.getTeacher(), newTime.minusMinutes(59), newTime.plusMinutes(59))) {
            throw new LessonTeacherConflictException("Nie mozna zaplanowac lekcji w tym terminie, nauczyciel jest zajety");
        }
        lesson.setDateTime(newTime);
        return lessonRepository.save(lesson);
    }

    public void deleteById(int id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Lesson.class.getSimpleName(), id));
        if (lesson.getDateTime().isBefore(LocalDateTime.now())) {
            throw new LessonInPastException(MessageFormat
                    .format("Lekcja pokrywa sie z inna lekcja {0}", lesson.getDateTime()));
        }
        lessonRepository.deleteById(id);
    }
}
