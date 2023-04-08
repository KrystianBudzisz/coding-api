package com.masters.coding.teacher;

import com.masters.coding.common.Language;
import com.masters.coding.teacher.model.Teacher;
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

    public void save(Teacher teacher) {
        teacher.setActive(true);
        teacherRepository.save(teacher);
    }

    public List<Teacher> findAllByLanguage(Language language) {
        return teacherRepository.findAllByLanguagesContaining(language);
    }

    public void deleteById(int id) {
        teacherRepository.deleteById(id);
    }
}
