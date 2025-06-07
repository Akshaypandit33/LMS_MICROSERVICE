package org.lms.userservice.Requests;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRoleRequests {
    private UUID userId;
    private String roleName;
}
