package com.masters.coding.teacher;

import com.masters.coding.common.Language;
import com.masters.coding.common.exception.TeacherNotFoundException;
import com.masters.coding.teacher.model.Teacher;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<Teacher> findAll() {
        return teacherRepository.findAllByActiveTrue();
    }

    public Teacher findById(int id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Nie znaleziono nauczyciela o danym id: " + id));
    }

    public List<Teacher> findAllByLanguage(Language language) {
        return teacherRepository.findAllByLanguagesContaining(language);
    }

    public Teacher save(Teacher teacher) {
        teacher.setActive(true);
        return teacherRepository.save(teacher);
    }

    public void deleteById(int id) {
        teacherRepository.deleteById(id);
    }

}
