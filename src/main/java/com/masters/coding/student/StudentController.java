package com.masters.coding.student;


import com.masters.coding.common.Language;
import com.masters.coding.student.model.Student;
import com.masters.coding.student.model.StudentDto;
import com.masters.coding.teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final TeacherService teacherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student, @RequestParam("teacherId") int teacherId) {
        return studentService.save(student, teacherId);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<StudentDto> findAllByTeacherId(@PathVariable("teacherId") int teacherId) {
        return studentService.findAllByTeacherId(teacherId).stream()
                .map(StudentDto::fromEntity)
                .toList();
    }

    @GetMapping("/{studentId}/update")
    public Student getStudentUpdate(@PathVariable int studentId) {
        return studentService.findById(studentId);
    }

    @PutMapping("/{studentId}/update")
    public Student updateStudent(@PathVariable int studentId, @RequestParam int teacherId) {
        return studentService.updateTeacher(studentId, teacherId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        studentService.deleteById(id);
    }

    @GetMapping("/createForm")
    public Map<String, Object> getStudentCreateForm() {
        Map<String, Object> formInfo = new HashMap<>();
        formInfo.put("languages", Language.values());
        formInfo.put("teachers", teacherService.findAll());
        formInfo.put("students", studentService.findAll());
        return formInfo;
    }

}
