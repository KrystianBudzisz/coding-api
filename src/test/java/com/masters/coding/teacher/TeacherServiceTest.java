package com.masters.coding.teacher;

import com.masters.coding.common.Language;
import com.masters.coding.teacher.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        teacher = new Teacher();
        teacher.setId(1);
        teacher.setFirstName("Alice");
        teacher.setLastName("Smith");
        teacher.setLanguages(Collections.singleton(Language.JAVA));
    }

    @Test
    public void findAllTest() {
        teacherService.findAll();
        verify(teacherRepository).findAllByActiveTrue();
    }

    @Test
    public void saveTest() {
        teacherService.save(teacher);
        verify(teacherRepository).save(teacher);
    }

    @Test
    public void findAllByLanguageTest() {
        teacherService.findAllByLanguage(Language.JAVA);
        verify(teacherRepository).findAllByLanguagesContaining(Language.JAVA);
    }

    @Test
    public void deleteByIdTest() {
        teacherService.deleteById(1);
        verify(teacherRepository).deleteById(1);
    }
}
