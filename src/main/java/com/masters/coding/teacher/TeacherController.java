package com.masters.coding.teacher;

import com.masters.coding.common.Language;
import com.masters.coding.teacher.model.Teacher;
import com.masters.coding.teacher.model.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public String getTeachersList(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teacher/list";
    }

    @GetMapping("/create")
    public String getTeacherCreateForm(Model model) {
        model.addAttribute("languages", Language.values());
        return "teacher/form";
    }

    @PostMapping("/create")
    public String createTeacher(Teacher teacher) {
        teacherService.save(teacher);
        return "redirect:/teachers";
    }

    @GetMapping(params = "language")
    @ResponseBody
    public List<TeacherDto> findAllByLanguage(@RequestParam Language language){
        return teacherService.findAllByLanguage(language).stream()
                .map(TeacherDto::fromEntity)
                .toList();
    }

    @DeleteMapping(params = "idToDelete")
    @ResponseBody
    public void deleteById(@RequestParam int idToDelete) {
        teacherService.deleteById(idToDelete);
    }
}
