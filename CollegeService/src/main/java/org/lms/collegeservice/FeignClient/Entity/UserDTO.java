package org.lms.collegeservice.FeignClient.Entity;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String collegeCode;
    private UUID collegeId;
    private Set<String> roles;
}
