package com.masters.coding.student;


import com.masters.coding.common.Language;
import com.masters.coding.student.model.Student;
import com.masters.coding.student.model.StudentDto;
import com.masters.coding.teacher.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.nio.cs.Surrogate.is;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @Mock
    private TeacherService teacherService;

    private static Student student;


//    @Test
//    public void testFindAllByTeacherId() throws Exception {
//        // given
//        int teacherId = 1;
//        List<StudentDto> students = Arrays.asList(
//                new Student(1, "John", "Doe", Language.JAVA),
//                new Student(2, "Jane", "Doe", Language.PYTHON)
//        );
//        given(studentService.findAllByTeacherId(teacherId)).willReturn(students);
//
//        // when
//        mockMvc.perform(get("/students").param("teacherId", String.valueOf(teacherId)))
//
//                // then
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].firstName").value(student.getFirstName()))
//                .andExpect(jsonPath("$[0].lastName").value(student.getLastName()))
//                .andExpect(jsonPath("$[0].language").value(student.getLanguage())))
//                .andExpect(jsonPath("$[1].id", is(2)))
//                .andExpect(jsonPath("$[1].firstName").value(student))
//                .andExpect(jsonPath("$[1].lastName", is("Doe")))
//                .andExpect(jsonPath("$[1].language", is("PYTHONv")));
//    }
//
//    private Student student;
//    private Teacher teacher;
//
//    @BeforeEach
//    public void setUp() {
//        student = new Student();
//        student.setId(1);
//        student.setFirstName("John");
//        student.setLastName("Doe");
//        student.setLanguage(Language.JAVA);
//
//        teacher = new Teacher();
//        teacher.setId(1);
//        teacher.setFirstName("Alice");
//        teacher.setLastName("Smith");
//        teacher.setLanguages(Collections.singleton(Language.JAVA));
//    }
//
//    @Test
//    public void getStudentList_shouldReturnListOfStudents() throws Exception {
//        List<StudentDto> students = new ArrayList<>();
//        when(studentService.findAll()).thenReturn(students);
//
//        mockMvc.perform(get("/students")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].firstName").value("John"))
//                .andExpect(jsonPath("$[0].lastName").value("Doe"))
//                .andExpect(jsonPath("$[0].language").value("JAVA"))
//                .andExpect(jsonPath("$[0].active").value(true))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].firstName").value("Jane"))
//                .andExpect(jsonPath("$[1].lastName").value("Doe"))
//                .andExpect(jsonPath("$[1].language").value("PYTHON"))
//                .andExpect(jsonPath("$[1].active").value(true));
//    }
//
//    @Test
//    public void getStudentCreateFormTest() {
//        studentController.getStudentCreateForm(model);
//        verify(teacherService).findAll();
//    }
//
//    @Test
//    public void createStudentTest() {
//        studentController.createStudent(student, 1);
//        verify(studentService).save(student, 1);
//    }
//
//    @Test
//    public void findAllByTeacherIdTest() {
//        List<StudentDto> studentDtos = studentController.findAllByTeacherId(1);
//        verify(studentService).findAllByTeacherId(1);
//    }
//
//    @Test
//    public void getStudentUpdateTest() {
//        when(studentService.findById(1)).thenReturn(student);
//        studentController.getStudentUpdate(1, model);
//        verify(teacherService).findAllByLanguage(student.getLanguage());
//    }
//
//    @Test
//    public void updateStudentTest() {
//        studentController.updateStudent(1, 1);
//        verify(studentService).updateTeacher(1, 1);
//    }
//
//    @Test
//    public void deleteByIdTest() {
//        studentController.deleteById(1);
//        verify(studentService).deleteById(1);
//    }
//}
