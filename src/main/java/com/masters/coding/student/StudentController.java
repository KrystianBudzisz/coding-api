package com.masters.coding.student;


import com.masters.coding.common.Language;
import com.masters.coding.student.model.CreateStudentCommand;
import com.masters.coding.student.model.Student;
import com.masters.coding.student.model.StudentDto;
import com.masters.coding.teacher.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody @Valid CreateStudentCommand command) {
        Student toSave = command.toEntity();
        return studentService.save(toSave, command.getTeacherId());
    }

    @GetMapping(params = "teacherId")
    public List<StudentDto> findAllByTeacherId(@RequestParam("teacherId") int teacherId) {
        return studentService.findAllByTeacherId(teacherId).stream()
                .map(StudentDto::fromEntity)
                .toList();
    }

    @GetMapping("/{studentId}")
    public Student getStudentUpdate(@PathVariable int studentId) {
        return studentService.findById(studentId);
    }

    @PutMapping("/{studentId}")
    public Student updateStudent(@PathVariable int studentId, @RequestParam int teacherId) {
        return studentService.updateTeacher(studentId, teacherId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        studentService.deleteById(id);
    }

}
