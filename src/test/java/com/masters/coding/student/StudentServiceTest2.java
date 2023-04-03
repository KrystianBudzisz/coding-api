//package com.masters.coding.student;
//
//import com.masters.coding.common.Language;
//import com.masters.coding.student.model.Student;
//import com.masters.coding.teacher.TeacherRepository;
//import com.masters.coding.teacher.model.Teacher;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.text.MessageFormat;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)     //zaprzęga do pracy Mockito (adnotacje @Mock oraz @InjectMocks)
//class StudentServiceTest {
//
//    @InjectMocks
//    private StudentService studentService;
//
//    @Mock
//    private StudentRepository studentRepository;
//
//    @Mock
//    private TeacherRepository teacherRepository;
//
//    @Captor
//    private ArgumentCaptor<Student> studentArgumentCaptor;
//
//    //poniższa metoda może zostać zastąpiona adnotacjami @Mock oraz @InjectMocks
////    @BeforeEach
////    void init() {
////        studentRepository = mock(StudentRepository.class);
////        teacherRepository = mock(TeacherRepository.class);
////        studentService = new StudentService(studentRepository, teacherRepository);
////    }
//
//    @Test
//    void testFindAll_ResultsInStudentListBeingReturned() {
//        //given
//        Student student = Student.builder()
//                .id(1)
//                .firstName("Test")
//                .lastName("Testowy")
//                .language(Language.JAVA)
//                .build();
//        List<Student> studentsFromRepo = List.of(student);
//
//        when(studentRepository.findAll()).thenReturn(studentsFromRepo);
//
//        //when
//        List<Student> returned = studentService.findAll();
//
//        //then
//        assertEquals(studentsFromRepo, returned);
//        verify(studentRepository).findAll();
//    }
//
//    @Test
//    void testSave_ResultsInStudentBeingSaved() {
//        //given
//        int teacherId = 1;
//        Student student = Student.builder()
//                .firstName("Test")
//                .lastName("Testowy")
//                .language(Language.JAVA)
//                .active(false)
//                .build();
//        Teacher teacher = Teacher.builder()
//                .id(teacherId)
//                .firstName("Test2")
//                .lastName("Testowy2")
//                .languages(Set.of(Language.JAVA))
//                .build();
//
//        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
//
//        //when
//        studentService.save(student, teacherId);
//
//        //then
//        verify(teacherRepository).findById(teacherId);
//
//        verify(studentRepository).save(studentArgumentCaptor.capture());
//        Student saved = studentArgumentCaptor.getValue();
//        assertEquals(student.getFirstName(), saved.getFirstName());
//        assertEquals(student.getLastName(), saved.getLastName());
//        assertEquals(student.getLanguage(), saved.getLanguage());
//        assertEquals(teacher, saved.getTeacher());
//        assertTrue(saved.isActive());
//    }
//
//    @Test
//    void testSave_TeacherFoundWithWrongLanguage_ResultsInIllegalArgumentException() {
//        //given
//        int teacherId = 1;
//        Language language = Language.JAVA;
//        String exceptionMsg = MessageFormat.format("Language {0} is not being taught by this teacher", language);
//        Student student = Student.builder()
//                .firstName("Test")
//                .lastName("Testowy")
//                .language(language)
//                .build();
//        Teacher teacher = Teacher.builder()
//                .id(teacherId)
//                .firstName("Test2")
//                .lastName("Testowy2")
//                .languages(Set.of(Language.PYTHON))
//                .build();
//
//        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
//
//        //when //then
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> studentService.save(student, teacherId));
//        assertEquals(exceptionMsg, exception.getMessage());
//
//
//        verify(teacherRepository).findById(teacherId);
//        verifyNoInteractions(studentRepository);
//    }
//
//    @Test
//    void testSave_TeacherNotFound_ResultsInEntityNotFoundException() {
//        //given
//        int teacherId = 1;
//        String exceptionMsg = MessageFormat.format("Teacher with id: {0} was not found", teacherId);
//        Student student = Student.builder()
//                .firstName("Test")
//                .lastName("Testowy")
//                .build();
//
//        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());
//
//        //when //then
////        EntityNotFoundException exception = assertThrows(
////                EntityNotFoundException.class,
////                () -> studentService.save(student, teacherId));
////        assertEquals(exceptionMsg, exception.getMessage());
//        assertThatExceptionOfType(EntityNotFoundException.class)
//                .isThrownBy(() -> studentService.save(student, teacherId))
//                .withMessage(exceptionMsg);
//
//        verify(teacherRepository).findById(teacherId);
//        verifyNoInteractions(studentRepository);
//    }
//
//}