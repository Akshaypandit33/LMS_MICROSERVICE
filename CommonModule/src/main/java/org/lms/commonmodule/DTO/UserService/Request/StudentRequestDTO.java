package org.lms.commonmodule.DTO.UserService.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDTO {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?1?[0-9]{9,15}", message = "Invalid phone number format (e.g., +1234567890 or 1234567890)")
    private String phoneNumber;

    private String universityRollNo; // Nullable?

    @NotBlank(message = "ERP ID cannot be blank")
    private String erpId;

    @NotBlank(message = "Branch Code cannot be blank")
    private String branchCode;

    @NotBlank(message = "Batch Year cannot be blank")
    @Size(min = 4, max = 4, message = "Batch year must be in 4-digit format (e.g., YYYY)")
    private String batchYear;

    @NotBlank(message = "Guardian name cannot be blank")
    private String guardianName;

    @NotBlank(message = "Guardian phone number cannot be blank")
    @Pattern(regexp = "^\\+?1?[0-9]{9,15}", message = "Invalid guardian phone number format (e.g., +1234567890 or 1234567890)")
    private String guardianPhone;


}

