package com.masters.coding.lesson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masters.coding.lesson.model.CreateLessonCommand;
import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.lesson.model.LessonDto;
import com.masters.coding.student.StudentRepository;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.TeacherRepository;
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

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Lesson lesson;

    @BeforeEach
    void init() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        Student savedStudent = studentRepository.saveAndFlush(student);

        Teacher teacher = new Teacher();
        teacher.setFirstName("Jane");
        teacher.setLastName("Smith");
        Teacher savedTeacher = teacherRepository.saveAndFlush(teacher);

        Lesson lessonToSave = Lesson.builder()
                .id(1)
                .student(savedStudent)
                .teacher(savedTeacher)
                .dateTime(LocalDateTime.now().plusDays(1))
                .active(true)
                .build();
        lesson = lessonRepository.saveAndFlush(lessonToSave);
    }

    @Test
    void shouldGetLessonList() throws Exception {
        mockMvc.perform(get("/api/lessons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(lesson.getId()))
                .andExpect(jsonPath("$.[0].studentId").value(lesson.getStudent().getId()))
                .andExpect(jsonPath("$.[0].teacherId").value(lesson.getTeacher().getId()))
                .andExpect(jsonPath("$.[0].dateTime").exists());
    }

    @Test
    void shouldCreateLesson() throws Exception {
        // Replace studentId and teacherId with actual values from your application
        CreateLessonCommand createLessonCommand = new CreateLessonCommand();
        createLessonCommand.setStudentId(1);
        createLessonCommand.setTeacherId(1);
        createLessonCommand.setDateTime(LocalDateTime.now().plusDays(2));

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createLessonCommand)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.studentId").value(createLessonCommand.getStudentId()))
                .andExpect(jsonPath("$.teacherId").value(createLessonCommand.getTeacherId()))
                .andExpect(jsonPath("$.dateTime").exists());
    }

    @Test
    void shouldGetLessonToUpdate() throws Exception {
        mockMvc.perform(get("/api/lessons/{lessonId}", lesson.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lesson.getId()))
                .andExpect(jsonPath("$.studentId").value(lesson.getStudent().getId()))
                .andExpect(jsonPath("$.teacherId").value(lesson.getTeacher().getId()))
                .andExpect(jsonPath("$.dateTime").exists());
    }

    @Test
    void shouldUpdateLesson() throws Exception {
        LocalDateTime newTime = LocalDateTime.now().plusDays(1);

        mockMvc.perform(put("/api/lessons/{lessonId}", lesson.getId())
                        .param("newTime", newTime.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lesson.getId()))
                .andExpect(jsonPath("$.studentId").value(lesson.getStudent().getId()))
                .andExpect(jsonPath("$.teacherId").value(lesson.getTeacher().getId()))
                .andExpect(jsonPath("$.dateTime").value(newTime.toString()));
    }

    @Test
    void shouldDeleteLessonById() throws Exception {
        assertTrue(lessonRepository.findById(lesson.getId())
                .filter(Lesson::isActive)
                .isPresent());

        mockMvc.perform(delete("/api/lessons/{id}", lesson.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(lessonRepository.findById(lesson.getId())
                .filter(Lesson::isActive)
                .isPresent());
    }

    @AfterEach
    void tearDown() {
        lessonRepository.deleteAll();
    }
}

