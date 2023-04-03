package com.masters.coding.lesson;

import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.student.StudentRepository;
import com.masters.coding.teacher.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @InjectMocks
    private LessonService lessonService;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    private Lesson lesson;

    @BeforeEach
    public void setUp() {
        lesson = new Lesson();
        lesson.setId(1);
        lesson.setDateTime(LocalDateTime.now().plusDays(1));
    }

    @Test
    public void findAllTest() {
        lessonService.findAll();
        verify(lessonRepository).findAll();
    }

    @Test
    public void saveTest() {
        lessonService.save(lesson, 1, 1);
        verify(studentRepository).findById(1);
        verify(teacherRepository).findById(1);
        verify(lessonRepository).save(lesson);
    }

    @Test
    public void deleteByIdTest() {
        lessonService.deleteById(1);
        verify(lessonRepository).findById(1);
        verify(lessonRepository).deleteById(1);
    }

    @Test
    public void updateLessonTimeTest() {
        LocalDateTime newTime = lesson.getDateTime().plusDays(2);
        lessonService.updateLessonTime(1, newTime);
        verify(lessonRepository).findById(1);
        verify(lessonRepository).save(lesson);
    }

    @Test
    public void findByIdTest() {
        lessonService.findById(1);
        verify(lessonRepository).findById(1);
    }
}
