package org.lms.commonmodule.DTO.UserService.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacultyRequestDTO {

    @NotNull(message = "First name is mandatory")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotNull(message = "Last name is mandatory")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be a 10-digit number")
    private String phoneNumber;

    @NotNull(message = "Employee ID is mandatory")
    @Size(min = 5, max = 20, message = "Employee ID must be between 5 and 20 characters")
    private String employeeId;

    private String collegeId;

    @NotEmpty(message = "Designation is mandatory")
    @Size(max = 50, message = "Designation must not exceed 50 characters")
    private String designation;

    @NotEmpty(message = "Qualification is mandatory")
    @Size(max = 255, message = "Qualification must not exceed 255 characters")
    private String qualification;

    @NotEmpty(message = "Specialization is mandatory")
    @Size(max = 255, message = "Specialization must not exceed 255 characters")
    private String specialization;

    // Getters and Setters
}

