package com.masters.coding.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masters.coding.common.Language;
import com.masters.coding.student.model.CreateStudentCommand;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.TeacherRepository;
import com.masters.coding.teacher.model.CreateTeacherCommand;
import com.masters.coding.teacher.model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentControllerTest {

    private Student student;
    private Teacher teacher;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void init() {
        teacher = teacherRepository.saveAndFlush(Teacher.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Smith")
                .languages(Collections.singleton(Language.JAVA))
                .active(true)
                .build());

        student = studentRepository.saveAndFlush(Student.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .language(Language.JAVA)
                .teacher(teacher)
                .active(true)
                .build());
    }

    @Test
    void shouldCreateStudent() throws Exception {

        CreateStudentCommand studentToSave = CreateStudentCommand.builder()
                .firstName("Jack")
                .lastName("Brown")
                .language(Language.JAVA)
                .build();

        assertFalse(studentRepository.findById(2)
                .filter(Student::isActive)
                .isPresent());

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentToSave)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value(studentToSave.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(studentToSave.getLastName()))
                .andExpect(jsonPath("$.language", hasSize(1)));
    }



    @Test
    void shouldFindStudentById() throws Exception {
        mockMvc.perform(get("/api/students/{studentId}", student.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.firstName").value(student.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(student.getLastName()));
    }

    @Test
    void shouldFindAllStudentsByTeacherId() throws Exception {
        mockMvc.perform(get("/api/students?teacherId=" + teacher.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(student.getId()))
                .andExpect(jsonPath("$.[0].firstName").value(student.getFirstName()))
                .andExpect(jsonPath("$.[0].lastName").value(student.getLastName()));
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        Student updatedStudent = Student.builder()
                .id(student.getId())
                .firstName("Jack")
                .lastName("Brown")
                .language(Language.JAVA)
                .teacher(teacher)
                .active(true)
                .build();

        CreateStudentCommand updateCommand = CreateStudentCommand.builder()
                .firstName(updatedStudent.getFirstName())
                .lastName(updatedStudent.getLastName())
                .language(updatedStudent.getLanguage())
                .teacherId(updatedStudent.getTeacher().getId())
                .build();

        mockMvc.perform(put("/api/students/{studentId}/update", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommand)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.firstName").value(updatedStudent.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedStudent.getLastName()));
    }



    @Test
    void shouldDeleteStudent() throws Exception {
        assertTrue(studentRepository.findById(1)
                .filter(Student::isActive)
                .isPresent());
        mockMvc.perform(delete("/api/students/{studentId}", student.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(studentRepository.findById(1)
                .filter(Student::isActive)
                .isPresent());    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
    }
}

