package org.lms.academicstructureservice.ServiceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lms.academicstructureservice.Feign.UserInterface;
import org.lms.academicstructureservice.Feign.UserRoleInterface;
import org.lms.academicstructureservice.Model.FacultyProfile;
import org.lms.academicstructureservice.Repository.FacultyProfileRepository;
import org.lms.academicstructureservice.Service.OrchestraService;
import org.lms.commonmodule.DTO.UserService.Request.CreateUserDto;
import org.lms.commonmodule.DTO.UserService.Request.FacultyRequestDTO;
import org.lms.commonmodule.DTO.UserService.Request.updateRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;
import org.lms.commonmodule.DTO.UserService.UserRoleDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.BusinessException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.ResourceAlreadyExistException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrchestraServiceImpl implements OrchestraService {
    private final UserInterface userInterface;
    private final UserRoleInterface userRoleInterface;
    private final FacultyProfileRepository facultyProfileRepository;

    @Override
    public UserResponseDTO createUser(CreateUserDto createUserDto) {
        UserResponseDTO savedUser = userInterface.createUser(createUserDto).getBody();
        if (savedUser == null) {
            throw new BusinessException("Unable to create user");
        }
        return savedUser;
    }

    @Override
    public void addUserRole(UUID userId, String roleName) {
        UserRoleDTO savedRole = userRoleInterface.createUserRole(userId, roleName).getBody();
        if (savedRole == null) {
            userRoleInterface.removeUserRole(userId, roleName);
            throw new BusinessException("Unable to create role");
        }

    }

    @Transactional
    @Override
    public FacultyProfile createFacultyProfile(String userId, FacultyRequestDTO facultyRequestDTO, String collegeId) {
        Optional<FacultyProfile> Profile = facultyProfileRepository
                .getFacultyProfileByUserIdAndCollegeId(userId,collegeId);
        if(Profile.isPresent()) {
            throw new ResourceAlreadyExistException("Faculty Profile already exist");
        }
        // Create Faculty Profile
        FacultyProfile facultyProfile = FacultyProfile.builder()
                .collegeId(facultyRequestDTO.getCollegeId())
                .designation(facultyRequestDTO.getDesignation())
                .employeeId(facultyRequestDTO.getEmployeeId())
                .qualification(facultyRequestDTO.getQualification())
                .specialization(facultyRequestDTO.getSpecialization())
                .userId(userId)
                .collegeId(collegeId)
                .build();
        FacultyProfile savedFaculty = facultyProfileRepository.save(facultyProfile);

        if (savedFaculty == null) {
            userRoleInterface.removeUserRole(UUID.fromString(userId), "FACULTY");
            userInterface.deleteUser(UUID.fromString(userId));
            throw new BusinessException("Unable to create faculty profile");
        }
        return savedFaculty;
    }

    @Override
    public void updateUser(String userId, String facultyId) {
        updateRequestDTO updateRequestDTO = new updateRequestDTO();
        updateRequestDTO.setProfileId(facultyId);
        userInterface.updateUser(UUID.fromString(userId), updateRequestDTO);
    }

    @Override
    public void removeUserRole(UUID userId, String roleName) {
        userRoleInterface.removeUserRole(userId, "FACULTY");
    }

    @Override
    public void removeUser(UUID userId) {
        userInterface.deleteUser(userId);
    }
}
