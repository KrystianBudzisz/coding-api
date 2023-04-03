package com.masters.coding.lesson;

import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.student.StudentService;
import com.masters.coding.teacher.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LessonControllerTest {

    @InjectMocks
    private LessonController lessonController;

    @Mock
    private LessonService lessonService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private StudentService studentService;

    @Mock
    private Model model;

    private Lesson lesson;

    @BeforeEach
    public void setUp() {
        lesson = new Lesson();
        lesson.setId(1);
        lesson.setDateTime(LocalDateTime.now().plusDays(1));
    }

    @Test
    public void getLessonListTest() {
        lessonController.getLessonList(model);
        verify(lessonService).findAll();
    }

    @Test
    public void getLessonCreateFormTest() {
        lessonController.getLessonCreateForm(model);
    }

    @Test
    public void createLessonTest() {
        lessonController.createLesson(lesson, 1, 1);
        verify(lessonService).save(lesson, 1, 1);
    }

    @Test
    public void deleteByIdTest() {
        lessonController.deleteById(1);
        verify(lessonService).deleteById(1);
    }

    @Test
    public void getLessonToUpdateTest() {
        lessonController.getLessonToUpdate(1, model);
        verify(lessonService).findById(1);
    }

    @Test
    public void updateLessonTest() {
        lessonController.updateLesson(1, lesson.getDateTime().plusDays(2), model);
        verify(lessonService).updateLessonTime(1, lesson.getDateTime().plusDays(2));
    }
}
