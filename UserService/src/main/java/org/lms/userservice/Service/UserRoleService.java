package org.lms.userservice.Service;


import org.lms.commonmodule.DTO.UserService.UserRoleDTO;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {

    UserRoleDTO addUserRole(UUID userId, String roleName);
    void removeRole(UUID userId, String roleName);
    List<UserRoleDTO> viewAllUserRolesByUserId(UUID userId);
    List<UserRoleDTO> getUserRolesByCollege(String collegeId);
    Boolean existsByUserId(UUID userId);
}
