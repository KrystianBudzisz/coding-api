package com.masters.coding.teacher;

import com.masters.coding.common.Language;
import com.masters.coding.teacher.model.Teacher;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    List<Teacher> findAllByLanguagesContaining(Language language);

    List<Teacher> findAllByActiveTrue();
}
