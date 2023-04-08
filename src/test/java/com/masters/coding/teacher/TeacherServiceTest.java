package com.masters.coding.teacher;

import com.masters.coding.common.Language;
import com.masters.coding.student.StudentRepository;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testFindAll_ResultsInTeacherListBeingReturned() {

        Teacher teacher = Teacher.builder()
                .id(1)
                .firstName("Tester")
                .lastName("Test")
                .languages(Collections.singleton(Language.JAVA))
                .build();
        List<Teacher> teacherFromRepo = List.of(teacher);

        when(teacherRepository.findAllByActiveTrue()).thenReturn(teacherFromRepo);

        List<Teacher> returned = teacherService.findAll();

        assertEquals(teacherFromRepo, returned);
        verify(teacherRepository).findAllByActiveTrue();
    }

    @Test
    void testSave() {
        Teacher teacher = Teacher.builder()
                .id(1)
                .firstName("Tester")
                .lastName("Test")
                .languages(Collections.singleton(Language.JAVA))
                .build();
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        teacherService.save(teacher);

        verify(teacherRepository).save(teacher);
    }

    @Test
    void findAllByLanguage() {
        Language language = Language.JAVA;
        teacherService.findAllByLanguage(language);
        verify(teacherRepository).findAllByLanguagesContaining(language);
    }

    @Test
    void deleteById() {
        int id = 1;
        teacherService.deleteById(id);
        verify(teacherRepository).deleteById(id);
    }
}
