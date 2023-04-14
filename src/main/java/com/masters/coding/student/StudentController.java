package com.masters.coding.student;

import com.masters.coding.student.model.CreateStudentCommand;
import com.masters.coding.student.model.Student;
import com.masters.coding.student.model.StudentDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> getStudentList() {
        return studentService.findAll()
                .stream()
                .map(StudentDto::fromEntity)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody @Valid CreateStudentCommand command) {
        Student toSave = command.toEntity();
        return studentService.save(toSave, command.getTeacherId());
    }

    @GetMapping("/{studentId}")
    public StudentDto getStudentUpdate(@PathVariable int studentId) {
        return StudentDto.fromEntity(studentService.findById(studentId));
    }

    @GetMapping(params = "teacherId")
    public List<StudentDto> findAllByTeacherId(@RequestParam("teacherId") int teacherId) {
        return studentService.findAllByTeacherId(teacherId).stream()
                .map(StudentDto::fromEntity)
                .toList();
    }

    @PutMapping("/{studentId}")
    public StudentDto updateStudent(@PathVariable int studentId, @RequestParam int teacherId) {
        return StudentDto.fromEntity(studentService.updateTeacher(studentId, teacherId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") int idToDelete) {
        studentService.deleteById(idToDelete);
    }

}
