package com.masters.coding.student;

import com.masters.coding.common.Language;
import com.masters.coding.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Repository //adnotacja do usunięcia w momencie zmiany repository na interface
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findAllByTeacherId(int teacherId);

//    private static final List<Student> dummyStudentDb = new ArrayList<>();
//
//    public List<Student> findAll() {
//        return dummyStudentDb;
//    }
//
//    @PostConstruct
//    private static void init() {
//        Student student = Student.builder()
//                .id(1)
//                .firstName("Michal")
//                .lastName("Nowak")
//                .build();
//        Student student1 = Student.builder()
//                .id(1)
//                .firstName("Wojtek")
//                .lastName("Byk")
//                .build();
//        dummyStudentDb.add(student);
//        dummyStudentDb.add(student1);
//    }
//
//    public Student save(Student student) {
//        dummyStudentDb.add(student);
//        student.setId(dummyStudentDb.size());
//        return student;
//    }


}
