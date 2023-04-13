package com.masters.coding.lesson;

import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.student.StudentService;
import com.masters.coding.student.model.Student;
import com.masters.coding.teacher.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lessons")

public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public List<Lesson> getLessonList() {
        return lessonService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lesson createLesson(@RequestBody Lesson lesson, @RequestParam("studentId") int studentId, @RequestParam("teacherId") int teacherId) {
        return lessonService.save(lesson, studentId, teacherId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        lessonService.deleteById(id);
    }

    @GetMapping("/{lessonId}")
    public Lesson getLessonToUpdate(@PathVariable int lessonId) {
        return lessonService.findById(lessonId);
    }

    @PutMapping("/{lessonId}")
    public Lesson updateLesson(@PathVariable int lessonId,
                               @RequestParam LocalDateTime newTime) throws IllegalArgumentException, EntityNotFoundException {
        return lessonService.updateLessonTime(lessonId, newTime);
    }


}
