package com.masters.coding.teacher;

import com.masters.coding.common.Language;
import com.masters.coding.teacher.model.Teacher;
import com.masters.coding.teacher.model.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherDto> getTeachersList() {
        return teacherService.findAll().stream().map(TeacherDto::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public TeacherDto findById(@PathVariable("id") int id) {
        return TeacherDto.fromEntity(teacherService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") int idToDelete) {
        teacherService.deleteById(idToDelete);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDto createTeacher(@RequestBody Teacher teacher) {
        return TeacherDto.fromEntity(teacherService.save(teacher));
    }


    @GetMapping(params = "language")
    public List<TeacherDto> findAllByLanguage(@RequestParam Language language) {
        return teacherService.findAllByLanguage(language).stream().map(TeacherDto::fromEntity).toList();
    }

    @GetMapping("/create-form")
    public String getTeacherCreateForm(Model model) {
        model.addAttribute("languages", Language.values());
        return "teacher/form";
    }

}
