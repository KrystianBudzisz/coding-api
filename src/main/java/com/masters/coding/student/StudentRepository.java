package com.masters.coding.student;

import com.masters.coding.common.Language;
import com.masters.coding.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository //adnotacja do usunięcia w momencie zmiany repository na interface
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllByTeacherId(int teacherId);


}
