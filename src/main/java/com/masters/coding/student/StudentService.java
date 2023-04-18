package com.masters.coding.student;

import com.masters.coding.common.exception.InvalidTeacherLanguageException;
import com.masters.coding.common.exception.NotFoundException;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.TeacherRepository;
import com.masters.coding.teacher.model.Teacher;
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
                .orElseThrow(() -> new NotFoundException(Student.class.getSimpleName(), studentId));
    }

    public List<Student> findAllByTeacherId(int teacherId) {
        return studentRepository.findAllByTeacherId(teacherId);
    }

    public Student save(Student student, int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(Teacher.class.getSimpleName(),teacherId));
        if (!teacher.getLanguages().contains(student.getLanguage())) {
            throw new InvalidTeacherLanguageException(MessageFormat
                    .format("Język {0} nie jest nauczany przez tego nauczyciela", student.getLanguage()));
        }
        student.setTeacher(teacher);
        student.setActive(true);
        studentRepository.save(student);
        return student;
    }

    public Student updateTeacher(int studentId, int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(Teacher.class.getSimpleName(), teacherId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(Student.class.getSimpleName(), studentId));

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
