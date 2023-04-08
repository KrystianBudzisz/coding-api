package com.masters.coding.student.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class StudentDto {

    private int id;
    private String firstName;
    private String lastName;

    public static StudentDto fromEntity (Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .build();
    }
}
