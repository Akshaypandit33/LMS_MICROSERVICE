package org.lms.userservice.DTOMapper;

import org.lms.commonmodule.DTO.UserService.UserRoleDTO;
import org.lms.userservice.Model.Role;
import org.lms.userservice.Model.User;
import org.lms.userservice.Model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserRoleMapper {
    public static UserRoleDTO convertToDTO(UserRole userRole) {
        if (userRole == null) return null;

        User user = userRole.getUser();
        Role role = userRole.getRole();

        assert user != null;
        return UserRoleDTO.builder()
                .id(userRole.getId())
                .userId(user.getId())
                .userEmail(user.getEmail())
                .userFullName(user.getFirstName() + " " + user.getLastName())
                .roleId(role != null ? role.getId() : null)
                .roleName(role != null ? role.getName() : null)
                .collegeId(user.getCollegeId()!= null ? UUID.fromString(user.getCollegeId()) : null)
                .build();
    }

    public static List<UserRoleDTO> convertListToDTO(List<UserRole> userRoles) {
        if (userRoles == null || userRoles.isEmpty()) {
            return new ArrayList<>();
        }
        return userRoles.stream()
                .map(UserRoleMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
