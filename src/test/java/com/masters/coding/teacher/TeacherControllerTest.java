//package com.masters.coding.teacher;
//
//import com.masters.coding.common.Language;
//import com.masters.coding.teacher.model.Teacher;
//import com.masters.coding.teacher.model.TeacherDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.ui.Model;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//public class TeacherControllerTest {
//
//    @InjectMocks
//    private TeacherController teacherController;
//
//    @Mock
//    private TeacherService teacherService;
//
//    @Mock
//    private Model model;
//
//    private Teacher teacher;

//    @BeforeEach
//    public void setUp() {
//        teacher = new Teacher();
//        teacher.setId(1);
//        teacher.setFirstName("Alice");
//        teacher.setLastName("Smith");
//        teacher.setLanguages(Collections.singleton(Language.JAVA));
//    }
//
//    @Test
//    public void getTeachersListTest() {
//        teacherController.getTeachersList(model);
//        verify(teacherService).findAll();
//    }
//
//    @Test
//    public void getTeacherCreateFormTest() {
//        teacherController.getTeacherCreateForm(model);
//    }
//
//    @Test
//    public void createTeacherTest() {
//        teacherController.createTeacher(teacher);
//        verify(teacherService).save(teacher);
//    }
//
//    @Test
//    public void findAllByLanguageTest() {
//        List<TeacherDto> teacherDtos = teacherController.findAllByLanguage(Language.JAVA);
//        verify(teacherService).findAllByLanguage(Language.JAVA);
//    }
//
//    @Test
//    public void deleteByIdTest() {
//        teacherController.deleteById(1);
//        verify(teacherService).deleteById(1);
//    }
//}