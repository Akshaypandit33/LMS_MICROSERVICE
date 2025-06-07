package org.lms.commonmodule.DTO.UserService.Request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class updateRequestDTO {
    String firstName;
    String lastName;
    String email;
    String password;
    String phoneNumber;
    String profileId;
    String collegeId;
}
