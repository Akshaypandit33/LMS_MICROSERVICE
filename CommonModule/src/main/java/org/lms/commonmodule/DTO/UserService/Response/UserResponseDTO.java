package org.lms.commonmodule.DTO.UserService.Response;

import lombok.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String collegeId;
    private String profileId;
    private List<String> roles = new ArrayList<>();

}
