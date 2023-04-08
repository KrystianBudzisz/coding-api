package com.masters.coding.lesson;

import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.student.StudentService;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lessons")

public class LessonController {

    private final LessonService lessonService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @GetMapping
    public String getLessonList(Model model){
        model.addAttribute("lessons",lessonService.findAll());
        return "lesson/list";
    }

    @GetMapping("/create")
    public String getLessonCreateForm(Model model){
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("students", studentService.findAll());
        return "lesson/form";
    }

    @PostMapping("/create")
    public String createLesson(Lesson lesson,
                               @RequestParam("studentId") int studentId,
                               @RequestParam("teacherId") int teacherId) {
        lessonService.save(lesson,studentId,teacherId);
        return "redirect:/lessons";
    }

    @DeleteMapping(params = "idToDelete")
    @ResponseBody
    public void deleteById(@RequestParam int idToDelete) {
        lessonService.deleteById(idToDelete);
    }

    @GetMapping("/update")
    public String getLessonToUpdate(@RequestParam int lessonId, Model model) {
        model.addAttribute("lesson", lessonService.findById(lessonId));
        return "lesson/changeDateTime";
    }
    @PostMapping("/update")
    public String updateLesson(@RequestParam int lessonId, @RequestParam LocalDateTime newTime, Model model) throws IllegalArgumentException, EntityNotFoundException {
        Lesson updatedLesson = lessonService.updateLessonTime(lessonId, newTime);
        model.addAttribute("lesson", updatedLesson);
        return "redirect: lesson/list";
    }
}
