package com.masters.coding.student;

import com.masters.coding.common.exception.InvalidTeacherLanguageException;
import com.masters.coding.common.exception.LanguageNotTaughtByTeacherException;
import com.masters.coding.common.exception.StudentNotFoundException;
import com.masters.coding.common.exception.TeacherNotFoundException;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.TeacherRepository;
import com.masters.coding.teacher.model.Teacher;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(int studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono ucznia o danym id: " + studentId));
    }

    public List<Student> findAllByTeacherId(int teacherId) {
        return studentRepository.findAllByTeacherId(teacherId);
    }

    public Student save(Student student, int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(MessageFormat
                        .format("Nauczyciel o id: {0} nie został znaleziony", teacherId)));
        if (!teacher.getLanguages().contains(student.getLanguage())) {
            throw new LanguageNotTaughtByTeacherException(MessageFormat
                    .format("Język {0} nie jest nauczany przez tego nauczyciela", student.getLanguage()));
        }
        student.setTeacher(teacher);
        student.setActive(true);
        studentRepository.save(student);
        return student;
    }

    public Student updateTeacher(int studentId, int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException("Nie znaleziono nauczyciela o danym id: " + teacherId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Nie znaleziono ucznia o danym id: " + studentId));

        if (!teacher.getLanguages().contains(student.getLanguage())) {
            throw new InvalidTeacherLanguageException("Język nauczyciela jest nieprawidłowy");
        }
        student.setTeacher(teacher);
        studentRepository.save(student);
        return student;
    }

    public void deleteById(int id) {
        studentRepository.deleteById(id);
    }
}
