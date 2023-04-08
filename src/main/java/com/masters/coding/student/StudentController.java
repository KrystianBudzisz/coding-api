package com.masters.coding.student;


import com.masters.coding.common.Language;
import com.masters.coding.student.model.Student;
import com.masters.coding.student.model.StudentDto;
import com.masters.coding.teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final TeacherService teacherService;

    @GetMapping
    public String getStudentList(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student/list";
    }

    @GetMapping("/create")
    public String getStudentCreateForm(Model model) {
        model.addAttribute("languages", Language.values());
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("student", studentService.findAll());

        return "student/form";
    }

    @PostMapping("/create")
    public String createStudent(Student student, @RequestParam("teacherId") int teacherId) {
        studentService.save(student, teacherId);
        return "redirect:/students";
    }

    @GetMapping(params = "teacher")
    @ResponseBody
    public List<StudentDto> findAllByTeacherId(@RequestParam("teacher") int teacherId) {
        return studentService.findAllByTeacherId(teacherId).stream()
                .map(StudentDto::fromEntity)
                .toList();

    }
    @GetMapping("/update")
    public String getTeacherUpdate(@RequestParam int studentId, Model model) {
        Student student = studentService.findById(studentId);
        model.addAttribute("student", student);
        model.addAttribute("teachers", teacherService.findAllByLanguage(student.getLanguage()));
        return "student/changeTeacher";
    }

    @PostMapping("/update")
    public String updateStudent(@RequestParam int studentId, @RequestParam int teacherId, Model model) {
        Student updatedStudent = studentService.updateTeacher(studentId, teacherId);
        model.addAttribute("student", updatedStudent);
        return "redirect:/students";
    }


    @DeleteMapping(params = "idToDelete")
    @ResponseBody
    public void deleteById(@RequestParam int idToDelete) {
        studentService.deleteById(idToDelete);
    }
}
