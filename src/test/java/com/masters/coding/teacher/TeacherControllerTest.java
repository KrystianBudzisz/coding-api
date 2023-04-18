package com.masters.coding.teacher;

import com.masters.coding.common.Language;
import com.masters.coding.teacher.model.Teacher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @BeforeEach
    void init() {
        Teacher teacherToSave = Teacher.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Smith")
                .languages(Collections.singleton(Language.JAVA))
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

    @AfterEach
    void teardown() {
        teacherRepository.deleteAll();
    }

}