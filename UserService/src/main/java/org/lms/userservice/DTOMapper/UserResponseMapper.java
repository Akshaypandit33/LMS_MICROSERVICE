package org.lms.userservice.DTOMapper;

import io.micrometer.common.lang.NonNull;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;
import org.lms.userservice.Model.Role;
import org.lms.userservice.Model.User;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserResponseMapper {
    public static UserResponseDTO from(User user) {
        return UserResponseDTO.builder()
                .userId(String.valueOf(user.getId()))
                .profileId(user.getProfileId() != null ? user.getProfileId().toString() : null)  // <-- Add this
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .collegeId(user.getCollegeId())
                .roles(user.getRoles() == null
                        ? new ArrayList<>()
                        : user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }


    public static User from(@NonNull UserResponseDTO userResponseDTO) {
        if (userResponseDTO.getUserId() == null || userResponseDTO.getUserId().isBlank()) {
            throw new IllegalArgumentException("User ID is null or blank in UserResponseDTO");
        }
        return User.builder()
                .id(UUID.fromString(userResponseDTO.getUserId()))
                .profileId(userResponseDTO.getProfileId() != null ? UUID.fromString(userResponseDTO.getProfileId()) : null)
                .firstName(userResponseDTO.getFirstName())
                .lastName(userResponseDTO.getLastName())
                .email(userResponseDTO.getEmail())
                .phoneNumber(userResponseDTO.getPhoneNumber())
                .collegeId(userResponseDTO.getCollegeId())
                .build();
    }

}
