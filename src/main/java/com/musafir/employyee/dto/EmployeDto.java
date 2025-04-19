package com.musafir.employyee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeDto {
    private Long id;
    @NotEmpty(message = "First Name can't be empty")
    private String firstName;
    @NotEmpty(message = "Last Name can't be empty")
    private String lastName;
    @NotEmpty(message = "Email can't be empty")
    @Email
    private String email;
    @NotEmpty(message = "Title couldn't be empty")
    private String jobTitle;
    @NotNull(message = "hire date can't be null")
    private LocalDateTime hireDate;
}
