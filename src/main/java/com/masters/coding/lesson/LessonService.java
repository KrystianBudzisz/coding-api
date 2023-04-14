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

    public Lesson findById(int lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Nie znaleziono lekcji o danym " + lessonId));
    }

    public Lesson save(Lesson lesson, int studentId, int teacherId) {
        LocalDateTime newTime = lesson.getDateTime();
        if (newTime.isBefore(LocalDateTime.now())) {
            throw new LessonInPastException("Lekcja nie moze byc tworzona w przeszlosci");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono studenta o id: " + studentId));
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Nie znaleziono nauczyciela o id: " + teacherId));


        if (lessonRepository.existsByTeacherAndDateTimeBetween(teacher, newTime.minusMinutes(59),
                newTime.plusMinutes(59))) {
            throw new LessonConflictException("Inna lekcja zostala juz rozpoczeta w tym czasie");
        }

        lesson.setStudent(student);
        lesson.setTeacher(teacher);

        lessonRepository.save(lesson);
        return lesson;
    }

    public Lesson updateLessonTime(int lessonId, LocalDateTime newTime) {
        if (newTime.isBefore(LocalDateTime.now())) {
            throw new LessonInPastException("Lekcja nie moze zostac stworzona w przeszlosci");
        }
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Nie znaleziono lekcji o danym id: " + lessonId));

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
                .orElseThrow(() -> new LessonNotFoundException("Nie znaleziono lekcji o id: " + id));
        if (lesson.getDateTime().plusMinutes(59).isBefore(LocalDateTime.now())) {
            throw new LessonTeacherConflictException(MessageFormat
                    .format("Lekcja pokrywa sie z inna lekcja {0}", lesson.getDateTime()));
        }
        lessonRepository.deleteById(id);
    }
}
