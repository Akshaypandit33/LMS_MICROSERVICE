package org.lms.academicstructureservice.Service;

import org.lms.academicstructureservice.Model.FacultyProfile;
import org.lms.commonmodule.DTO.UserService.HODResponseDTO;
import org.lms.commonmodule.DTO.UserService.Request.CreateUserDto;
import org.lms.commonmodule.DTO.UserService.Request.FacultyRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.FacultyResponseDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;

import java.util.UUID;

public interface OrchestraService {


    UserResponseDTO createUser(CreateUserDto createUserDto);
    void addUserRole(UUID userId, String roleName);
    FacultyProfile createFacultyProfile(String userId, FacultyRequestDTO facultyRequestDTO, String collegeId);
    void updateUser(String userId, String facultyId);

    void removeUserRole(UUID userId, String roleName);
    void removeUser(UUID userId);


}
