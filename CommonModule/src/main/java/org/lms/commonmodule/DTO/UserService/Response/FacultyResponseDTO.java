package org.lms.commonmodule.DTO.UserService.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacultyResponseDTO {
    String facultyId;
    String userId;
    String firstName;
    String lastName;
    String email;
    String password;
    String phoneNumber;
    String employeeId;
    String collegeId;
    String designation;
    String qualification;
    String specialization;
}
