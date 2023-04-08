package com.masters.coding.lesson;

import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.student.StudentRepository;
import com.masters.coding.student.StudentService;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.TeacherRepository;
import com.masters.coding.teacher.model.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {


    @InjectMocks
    private LessonService lessonService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Test
    void testSaveLessonSuccess() {
        Lesson lesson = new Lesson();
        lesson.setDateTime(LocalDateTime.now().plusHours(1));
        Student student = new Student();
        student.setId(1);
        Teacher teacher = new Teacher();
        teacher.setId(1);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher));
        when(lessonRepository.existsByTeacherAndDateTimeBetween(teacher, lesson.getDateTime().minusMinutes(59), lesson.getDateTime().plusMinutes(59))).thenReturn(false);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        assertDoesNotThrow(() -> lessonService.save(lesson, 1, 1));
        assertEquals(student, lesson.getStudent());
        assertEquals(teacher, lesson.getTeacher());
    }

//    @Test
//    public void testUpdateLessonTime() {
//        LocalDateTime newTime = LocalDateTime.of(2023, 4, 1, 11, 0);
//        when(lessonRepository.findById(lesson.getId())).thenReturn(Optional.of(lesson));
//        when(lessonRepository.existsByTeacherAndDateTimeBetween(lesson.getTeacher(), newTime.minusMinutes(59), newTime.plusMinutes(59))).thenReturn(false);
//        Lesson updatedLesson = lessonService.updateLessonTime(lesson.getId(), newTime);
//        assertEquals(newTime, updatedLesson.getDateTime());
//        verify(lessonRepository, times(1)).save(lesson);
//    }
//
//    @Test
//    public void testUpdateLessonTimeWithPastDate() {
//        LocalDateTime newTime = LocalDateTime.of(2022, 4, 1, 10, 0);
//        lessonService.updateLessonTime(lesson.getId(), newTime);
//    }
//
//    @Test
//    public void testUpdateLessonTimeForAlreadyStartedLesson() {
//        LocalDateTime newTime = LocalDateTime.of(2023, 4, 1, 11, 0);
//        lesson.setDateTime(LocalDateTime.of(2023, 4, 1, 9, 0));
//        when(lessonRepository.findById(lesson.getId())).thenReturn(Optional.of(lesson));
//        lessonService.updateLessonTime(lesson.getId(), newTime);
//    }
//
//    @Test
//    public void testUpdateLessonTimeForTeacherAlreadyBusy() {
//        LocalDateTime newTime = LocalDateTime.of(2023, 4, 1, 11, 0);
//        when(lessonRepository.findById(lesson.getId())).thenReturn(Optional.of(lesson));
//        when(lessonRepository.existsByTeacherAndDateTimeBetween(lesson.getTeacher(), newTime.minusMinutes(59), newTime.plusMinutes(59))).thenReturn(true);
//        lessonService.updateLessonTime(lesson.getId(), newTime);
//    }


}
