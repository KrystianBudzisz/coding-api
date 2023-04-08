package com.masters.coding.teacher.model;

import com.masters.coding.common.Language;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class TeacherDto {

    private int id;
    private String firstName;
    private String lastName;
    private Set<Language> languages;

    public static TeacherDto fromEntity(Teacher teacher) {
        return TeacherDto.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .languages(teacher.getLanguages())
                .build();
    }
}
