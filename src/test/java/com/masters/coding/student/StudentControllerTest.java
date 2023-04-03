package com.masters.coding.student;



import com.masters.coding.common.Language;
import com.masters.coding.student.model.Student;
import com.masters.coding.student.model.StudentDto;
import com.masters.coding.teacher.TeacherService;
import com.masters.coding.teacher.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @Mock
    private TeacherService teacherService;

    @Mock
    private Model model;

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
    public void getStudentListTest() {
        studentController.getStudentList(model);
        verify(studentService).findAll();
    }

    @Test
    public void getStudentCreateFormTest() {
        studentController.getStudentCreateForm(model);
        verify(teacherService).findAll();
    }

    @Test
    public void createStudentTest() {
        studentController.createStudent(student, 1);
        verify(studentService).save(student, 1);
    }

    @Test
    public void findAllByTeacherIdTest() {
        List<StudentDto> studentDtos = studentController.findAllByTeacherId(1);
        verify(studentService).findAllByTeacherId(1);
    }

    @Test
    public void getStudentUpdateTest() {
        when(studentService.findById(1)).thenReturn(student);
        studentController.getStudentUpdate(1, model);
        verify(teacherService).findAllByLanguage(student.getLanguage());
    }

    @Test
    public void updateStudentTest() {
        studentController.updateStudent(1, 1);
        verify(studentService).updateTeacher(1, 1);
    }

    @Test
    public void deleteByIdTest() {
        studentController.deleteById(1);
        verify(studentService).deleteById(1);
    }
}
