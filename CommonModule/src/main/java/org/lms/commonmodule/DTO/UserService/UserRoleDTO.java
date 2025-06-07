package org.lms.commonmodule.DTO.UserService;

import lombok.*;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDTO {
    private UUID id;
    private UUID userId;
    private String userEmail;
    private String userFullName;
    private UUID roleId;
    private String roleName;
    private UUID collegeId;


}