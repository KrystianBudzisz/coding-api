package com.masters.coding.lesson;

import com.masters.coding.common.exception.TermNotAvailableException;
import com.masters.coding.lesson.model.CreateLessonCommand;
import com.masters.coding.lesson.model.Lesson;
import com.masters.coding.lesson.model.LessonDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lessons")

public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public List<LessonDto> getLessonList() {
        return lessonService.findAll()
                .stream()
                .map(LessonDto::fromEntity)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto createLesson(@RequestBody @Valid CreateLessonCommand commad) {
        Lesson toSave = commad.toEntity();
        return LessonDto.fromEntity(lessonService.save(toSave, commad.getStudentId(), commad.getTeacherId()));
    }

    @GetMapping("/{lessonId}")
    public LessonDto getLessonToUpdate(@PathVariable int lessonId) {
        return LessonDto.fromEntity(lessonService.findById(lessonId));
    }

    @PutMapping("/{lessonId}")
    public LessonDto updateLesson(@PathVariable int lessonId,
                               @RequestParam LocalDateTime newTime)
            throws TermNotAvailableException {
        return LessonDto.fromEntity(lessonService.updateLessonTime(lessonId, newTime));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") int idToDelete) {
        lessonService.deleteById(idToDelete);
    }

}
