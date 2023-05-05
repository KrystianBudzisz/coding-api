package com.masters.coding.teacher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masters.coding.common.Language;
import com.masters.coding.teacher.model.CreateTeacherCommand;
import com.masters.coding.teacher.model.Teacher;
import org.hamcrest.Matchers;
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
import java.util.Optional;

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
class TeacherControllerTest {

    private static Teacher teacher;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        Teacher teacherToSave = Teacher.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Smith")
                .languages(Collections.singleton(Language.JAVA))
                .active(true)
                .build();
        teacher = teacherToSave;
        teacherRepository.saveAndFlush(teacherToSave);
    }

    @Test
    void shouldGetSingleTeacher() throws Exception {

        mockMvc.perform(get("/api/teachers/1"))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value(teacher.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(teacher.getLastName()))

                .andExpect(jsonPath("$.languages").isArray())
//                .andExpect(jsonPath("$.languages").isEmpty())
                .andExpect(jsonPath("$.languages", hasSize(teacher.getLanguages().size())))
        ;
    }

    @Test
    void testFindById_TeacherNotFound_ResultsInNotFoundException() throws Exception {

        String exceptionMsg = "Teacher with id=0 not found";

        mockMvc.perform(get("/api/teachers/0"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").value(exceptionMsg))
        ;
    }

    @Test
    void shouldGetAllTeachers() throws Exception {

        mockMvc.perform(get("/api/teachers"))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].firstName").value(teacher.getFirstName()))
                .andExpect(jsonPath("$.[0].lastName").value(teacher.getLastName()))

                .andExpect(jsonPath("$.[0].languages").isArray())
//                .andExpect(jsonPath("$.languages").isEmpty())
                .andExpect(jsonPath("$.[0].languages", hasSize(teacher.getLanguages().size())))
        ;
    }

    @Test
    void shouldGetTeachersByLanguage() throws Exception {

        String language = Language.JAVA.name();
        mockMvc.perform(get("/api/teachers?language=" + language))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].firstName").value(teacher.getFirstName()))
                .andExpect(jsonPath("$.[0].lastName").value(teacher.getLastName()))

                .andExpect(jsonPath("$.[0].languages").isArray())
//                .andExpect(jsonPath("$.languages").isEmpty())
                .andExpect(jsonPath("$.[0].languages", hasSize(teacher.getLanguages().size())))
                .andExpect(jsonPath("$.[0].languages",contains(language)))
        ;
    }

    @Test
    void shouldDeleteTeacherById() throws Exception {
        assertTrue(teacherRepository.findById(1)
                .filter(Teacher::isActive)
                .isPresent());

        mockMvc.perform(delete("/api/teachers/1"))
                .andDo(print())
                .andExpect(status().isNoContent())
        ;

        assertFalse(teacherRepository.findById(1)

                .isPresent());
    }

    @Test
    void shouldCreateTeacher() throws Exception {

        CreateTeacherCommand teacherToSave = CreateTeacherCommand.builder()
//                .id(1)
                .firstName("Dupa")
                .lastName("Jasiu")
                .languages(Collections.singleton(Language.PYTHON))
//                .active(true)
                .build();

        assertFalse(teacherRepository.findById(2)
                .filter(Teacher::isActive)
                .isPresent());

        mockMvc.perform(post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherToSave)))
                .andDo(print())
                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value(teacherToSave.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(teacherToSave.getLastName()))

                .andExpect(jsonPath("$.languages").isArray())
//                .andExpect(jsonPath("$.languages").isEmpty())
                .andExpect(jsonPath("$.languages", hasSize(teacherToSave.getLanguages().size())))
        ;

        Teacher newTeacherToSave = teacherRepository.findById(1).orElseThrow();


    }




    @AfterEach
    void teardown() {
        teacherRepository.deleteAll();
    }

}