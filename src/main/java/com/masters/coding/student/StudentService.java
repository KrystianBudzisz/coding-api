package com.masters.coding.student;

import com.masters.coding.lesson.model.Lesson;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public void save(Student student, int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Teacher with id: {0} was not found", teacherId)));

        if (!teacher.getLanguages().contains(student.getLanguage())) {
            throw new IllegalArgumentException(MessageFormat
                    .format("Language {0} is not being taught by this teacher", student.getLanguage()));
        }

        student.setTeacher(teacher);
        student.setActive(true);
        studentRepository.save(student);
    }
    public Student updateTeacher(int studentId, int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono nauczyciel o danym id: " + teacherId));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono studenta o danym id: " + studentId));
       if(!teacher.getLanguages().contains(student.getLanguage())) {
           throw new IllegalArgumentException("Jezyk nauczyciela jest nie prawidlowy ");
       }
        return studentRepository.save(student);
    }



    public void deleteById(int id) {
        studentRepository.deleteById(id);
    }

    public List<Student> findAllByTeacherId(int teacherId) {
        return studentRepository.findAllByTeacherId(teacherId);
    }
    public Student findById(int studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono lekcji o danym " + studentId));
    }
}
