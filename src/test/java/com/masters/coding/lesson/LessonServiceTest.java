package com.masters.coding.lesson;

import com.masters.coding.common.exception.NotFoundException;
import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.student.StudentRepository;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.TeacherRepository;
import com.masters.coding.teacher.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(new Student()));
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.of(new Teacher()));
//        when(lessonRepository.existsByTeacherAndDateTimeBetween(any(Teacher.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(false);

        lessonService.save(lesson, 1, 1);

        verify(studentRepository).findById(1);
        verify(teacherRepository).findById(1);
        verify(lessonRepository).save(lesson);
    }

    // Add test for the exception case when student is not found
    @Test
    public void saveTest_StudentNotFound() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            lessonService.save(lesson, 1, 1);
        });
    }

    @Test
    public void deleteByIdTest() {
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));

        lessonService.deleteById(1);

        verify(lessonRepository).findById(1);
        verify(lessonRepository).deleteById(1);
    }

    // Add test for the exception case when lesson is not found
    @Test
    public void deleteByIdTest_LessonNotFound() {
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            lessonService.deleteById(1);
        });
    }

    @Test
    public void updateLessonTimeTest() {
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(lesson));
//        when(lessonRepository.existsByTeacherAndDateTimeBetween(any(Teacher.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(false);

        LocalDateTime newTime = lesson.getDateTime().plusDays(2);
        lessonService.updateLessonTime(1, newTime);

        verify(lessonRepository).findById(1);
        verify(lessonRepository).save(lesson);
    }

    @Test
    public void updateLessonTimeTest_LessonNotFound() {
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.empty());

        LocalDateTime newTime = LocalDateTime.now().plusDays(2);

        assertThrows(NotFoundException.class, () -> {
            lessonService.updateLessonTime(1, newTime);
        });
    }

    @Test
    public void findByIdTest_existingLesson() {
        Lesson expectedLesson = new Lesson();
        expectedLesson.setId(1);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(expectedLesson));

        Lesson actualLesson = lessonService.findById(1);

        verify(lessonRepository).findById(1);
        assertEquals(expectedLesson, actualLesson);
    }

    @Test
    public void findByIdTest_nonExistingLesson() {
        when(lessonRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> lessonService.findById(1));
        verify(lessonRepository).findById(1);
    }
}
