package org.lms.academicstructureservice.ServiceImpl;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.lms.academicstructureservice.DTOMapper.FacultyResponseMapper;
import org.lms.academicstructureservice.Feign.UserInterface;
import org.lms.academicstructureservice.Feign.UserRoleInterface;
import org.lms.academicstructureservice.Model.FacultyProfile;
import org.lms.academicstructureservice.Repository.FacultyProfileRepository;
import org.lms.academicstructureservice.Service.FacultyProfileService;
import org.lms.academicstructureservice.Service.OrchestraService;
import org.lms.commonmodule.DTO.UserService.Request.CreateUserDto;
import org.lms.commonmodule.DTO.UserService.Request.FacultyRequestDTO;
import org.lms.commonmodule.DTO.UserService.Request.updateRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.FacultyResponseDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.BusinessException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.ResourceNotFoundException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.UserServiceExceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FacultyProfileServiceImpl implements FacultyProfileService {
    private final FacultyProfileRepository facultyProfileRepository;
    private final OrchestraService orchestraService;
    private final UserInterface userInterface;
    private final UserRoleInterface userRoleInterface;


    @Transactional
    public FacultyResponseDTO addStaff(@NonNull FacultyRequestDTO facultyRequestDTO, String collegeId, String roleName) {
        CreateUserDto userRequestDTO = CreateUserDto.builder()
                .firstName(facultyRequestDTO.getFirstName())
                .lastName(facultyRequestDTO.getLastName())
                .email(facultyRequestDTO.getEmail())
                .password(facultyRequestDTO.getPassword())
                .collegeId(collegeId)
                .phoneNumber(facultyRequestDTO.getPhoneNumber())
                .build();

        UUID userId = null;
        try {
            // First Step: Create User
            UserResponseDTO user =orchestraService.createUser(userRequestDTO);
            userId = UUID.fromString(user.getUserId());
            // Second Step: Assign Role to User
            orchestraService.addUserRole(UUID.fromString(user.getUserId()), roleName);

            // Third Step: Create Faculty Profile
            FacultyProfile createdFaculty = orchestraService.createFacultyProfile(user.getUserId(), facultyRequestDTO,collegeId);

            // Fourth Step: Update the User to Include Faculty ID
            orchestraService.updateUser(user.getUserId(), String.valueOf(createdFaculty.getId()));

            // Return created Faculty DTO
            return FacultyResponseMapper.from(createdFaculty, user);
        } catch (BusinessException e) {
            // Rollback Steps if any error occurs

            rollback(userId,"FACULTY");
            throw e;
        }
    }

    private void rollback(UUID userId, String roleName) {
        orchestraService.removeUserRole(userId, roleName);
        orchestraService.removeUser(userId);
    }

    @Transactional
    @Override
    public FacultyResponseDTO createFaculty(@NonNull FacultyRequestDTO facultyRequestDTO, String collegeId) {
        return addStaff(facultyRequestDTO,collegeId,"FACULTY");
    }

    @Transactional
    @Override
    public FacultyResponseDTO updateFaculty(String userId, @NonNull FacultyRequestDTO facultyRequestDTO, String collegeId) {
        updateRequestDTO userRequest = updateRequestDTO.builder()
                .firstName(facultyRequestDTO.getFirstName())
                .lastName(facultyRequestDTO.getLastName())
                .email(facultyRequestDTO.getEmail())
                .password(facultyRequestDTO.getPassword())
                .phoneNumber(facultyRequestDTO.getPhoneNumber())
                .build();
        UserResponseDTO userResponseDTO = userInterface.updateUser(UUID.fromString(userId),userRequest).getBody();
        assert userResponseDTO != null;
        FacultyProfile facultyProfile = facultyProfileRepository.findById(UUID.fromString(userResponseDTO.getProfileId())).orElseThrow(
                () -> new UserNotFoundException("Faculty not found "+userResponseDTO.getProfileId())
        );
        if(facultyRequestDTO.getDesignation() != null && !facultyRequestDTO.getDesignation().isEmpty()) {
            facultyProfile.setDesignation(facultyRequestDTO.getDesignation());
        }
        if(facultyRequestDTO.getQualification() != null && !facultyRequestDTO.getQualification().isEmpty()) {
            facultyProfile.setQualification(facultyRequestDTO.getQualification());
        }
        if(facultyRequestDTO.getEmployeeId() != null && !facultyRequestDTO.getEmployeeId().isEmpty()) {
            facultyProfile.setEmployeeId(facultyRequestDTO.getEmployeeId());
        }
        FacultyProfile savedFaculty = facultyProfileRepository.save(facultyProfile);
        return FacultyResponseMapper.from(savedFaculty,userResponseDTO);
    }

    @Override
    public List<FacultyResponseDTO> getAllFaculty(@NonNull String CollegeId) {

        List<FacultyProfile> facultyProfileList = facultyProfileRepository.getFacultyProfilesByCollegeId(CollegeId);
        List<FacultyResponseDTO> facultyResponseDTOList = new ArrayList<>();

        for(FacultyProfile facultyProfile : facultyProfileList) {
            UserResponseDTO userResponseDTO =
                    userInterface.getUserById(UUID.fromString(facultyProfile.getUserId())).getBody();
            assert userResponseDTO != null;
            facultyResponseDTOList.add(FacultyResponseMapper.from(facultyProfile,userResponseDTO));
        }
        return facultyResponseDTOList;
    }

    @Transactional
    @Override
    public void deleteFaculty(@NonNull String userId, @NonNull String collegeId) {

        UserResponseDTO user = userInterface.getUserById(UUID.fromString(userId)).getBody();
        Optional<FacultyProfile> Profile = facultyProfileRepository
                .findById(UUID.fromString(Objects.requireNonNull(user).getProfileId()));
        try{
            orchestraService.removeUserRole(UUID.fromString(user.getUserId()), "FACULTY");
            Boolean exists = userRoleInterface.checkUserHaveRolesOrNot(userId).getBody();
            if(!exists){
                facultyProfileRepository.deleteById(Profile.get().getId());
                orchestraService.removeUser(UUID.fromString(user.getUserId()));
            }

        }
        catch(BusinessException ex){
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public FacultyResponseDTO getFacultyByUserId(String userId, @NonNull String CollegeId) {
        UserResponseDTO user = userInterface.getUserById(UUID.fromString(userId)).getBody();
        assert user != null;
        FacultyProfile facultyProfile = facultyProfileRepository.findById(UUID.fromString(user.getProfileId())).orElseThrow(
                () -> new UserNotFoundException("Faculty not found "+user.getProfileId())
        );
        return FacultyResponseMapper.from(facultyProfile,user);
    }
}
