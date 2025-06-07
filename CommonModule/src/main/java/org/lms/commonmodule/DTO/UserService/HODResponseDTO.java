package org.lms.commonmodule.DTO.UserService;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HODResponseDTO {
    String employeeId;
    String userId;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
}
