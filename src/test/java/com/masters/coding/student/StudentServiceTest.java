package com.masters.coding.student;

import com.masters.coding.common.Language;
import com.masters.coding.common.exception.InvalidTeacherLanguageException;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.TeacherRepository;
import com.masters.coding.teacher.model.Teacher;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    private Student student;
    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setId(1);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setLanguage(Language.JAVA);

        teacher = new Teacher();
        teacher.setId(1);
        teacher.setFirstName("Alice");
        teacher.setLastName("Smith");
        teacher.setLanguages(Collections.singleton(Language.JAVA));
    }

    @Test
    public void findAllTest() {
        studentService.findAll();
        verify(studentRepository).findAll();
    }

    @Test
    public void saveStudentTest() {
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));

        studentService.save(student, 1);

        verify(studentRepository).save(student);
    }

    @Test
    public void saveStudentInvalidTeacherLanguageTest() {

        Set<Language> languages = new HashSet<>(teacher.getLanguages());
        languages.remove(Language.JAVA);
        teacher.setLanguages(languages);
        student.setLanguage(Language.JAVA);

        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));

        assertThrows(InvalidTeacherLanguageException.class, () -> studentService.save(student, 1));
        verify(teacherRepository).findById(1);
    }

    @Test
    public void updateTeacherTest() {
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        studentService.updateTeacher(1, 1);

        verify(studentRepository).save(student);
    }

    @Test

    public void updateTeacherInvalidTeacherLanguageTest() {
        Set<Language> languages = new HashSet<>(teacher.getLanguages());
        languages.remove(Language.JAVA);
        teacher.setLanguages(languages);
        student.setLanguage(Language.JAVA);

        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        assertThrows(InvalidTeacherLanguageException.class, () -> studentService.updateTeacher(1, 1));
        verify(teacherRepository).findById(1);
        verify(studentRepository).findById(1);
    }


    @Test
    public void deleteByIdTest() {
        studentService.deleteById(1);
        verify(studentRepository).deleteById(1);
    }

    @Test
    public void findAllByTeacherIdTest() {
        studentService.findAllByTeacherId(1);
        verify(studentRepository).findAllByTeacherId(1);
    }

    @Test
    public void findByIdTest() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        studentService.findById(1);

        verify(studentRepository).findById(1);
    }

    @Test
    public void findByIdNotFoundTest() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.findById(1));
    }
}
