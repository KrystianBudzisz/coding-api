package com.masters.coding.teacher.model;

import com.masters.coding.common.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class CreateTeacherCommand {

    @NotBlank(message = "first name cannot be blank")
    @Pattern(regexp = "[A-Z][a-z]{1,19}", message = "first name has to match the pattern {regexp}")
    private String firstName;

    @NotBlank(message = "last name cannot be blank")
    @Pattern(regexp = "[A-Z][a-z]{1,29}", message = "last name has to match the pattern {regexp}")
    private String lastName;

    @NotEmpty(message = "at least 1 language required")
    private Set<Language> languages;

    public Teacher toEntity() {
        return Teacher.builder()
                .firstName(firstName)
                .lastName(lastName)
                .languages(languages)
                .active(true)
                .build();
    }
}
