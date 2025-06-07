package org.lms.userservice.Service;

import org.lms.commonmodule.DTO.UserService.Request.CreateUserDto;
import org.lms.commonmodule.DTO.UserService.Request.updateRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;


import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO createUser(CreateUserDto request);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO  getUserById(UUID id);
   UserResponseDTO getUserByEmail(String email);

    UserResponseDTO updateUser(UUID userId, updateRequestDTO request);
    void deleteUser(UUID userId);
    Boolean userExists(String email, String collegeId);
}
