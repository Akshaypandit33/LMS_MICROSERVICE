package org.lms.collegeservice.FeignClient.Entity;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRoleDTO {
    private UUID id;
    private UUID userId;
    private String userEmail;
    private String userFullName;
    private UUID roleId;
    private String roleName;
}
