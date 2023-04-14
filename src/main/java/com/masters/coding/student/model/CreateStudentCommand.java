package com.masters.coding.student.model;

import com.masters.coding.common.Language;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateStudentCommand {

    @NotBlank(message = "first name cannot be blank")
    @Pattern(regexp = "[A-Z][a-z]{1,19}", message = "first name has to match the pattern {regexp}")
    private String firstName;

    @NotBlank(message = "last name cannot be blank")
    @Pattern(regexp = "[A-Z][a-z]{1,29}", message = "last name has to match the pattern {regexp}")
    private String lastName;

    @NotNull(message = "language required")
    private Language language;

    @Positive(message = "teacher id has to be a positive num")
    private int teacherId;

    public Student toEntity() {
        return Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .language(language)
                .active(true)
                .build();
    }
}
