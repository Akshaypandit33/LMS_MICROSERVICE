package org.lms.userservice.ServiceImplementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lms.commonmodule.DTO.UserService.Request.CreateUserDto;
import org.lms.commonmodule.DTO.UserService.Request.StudentRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.StudentResponseDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.CreationException;
import org.lms.userservice.Feign.BranchServiceInterface;
import org.lms.userservice.Model.StudentProfile;
import org.lms.userservice.Repository.StudentRepository;
import org.lms.userservice.Service.StudentService;
import org.lms.userservice.Service.UserRoleService;
import org.lms.userservice.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private UserService userService;
    private UserRoleService userRoleService;
    private BranchServiceInterface branchServiceInterface;


// incomplete
    @Transactional
    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO, String collegeId) {
        Boolean exists = branchServiceInterface.existsByBranchCode(studentRequestDTO.getBranchCode(),collegeId).getBody();
        UserResponseDTO userResponseDTO = userService.createUser(CreateUserDto.builder()
                        .firstName(studentRequestDTO.getFirstName())
                        .lastName(studentRequestDTO.getLastName())
                        .collegeId(collegeId)
                        .email(studentRequestDTO.getEmail())
                        .password(studentRequestDTO.getPassword())
                        .phoneNumber(studentRequestDTO.getPhoneNumber())
                .build());
        if(userResponseDTO.getUserId() == null) {
            throw new CreationException("Unable to create student");
        }
        userRoleService.addUserRole(UUID.fromString(userResponseDTO.getUserId()),"STUDENT");
        StudentProfile studentProfile = StudentProfile.builder()
                .batchYear(studentRequestDTO.getBatchYear())
                .erpId(studentRequestDTO.getErpId())
                .collegeId(collegeId)
                .guardianName(studentRequestDTO.getGuardianName())
                .universityRollNo(studentRequestDTO.getUniversityRollNo())
                .guardianName(studentRequestDTO.getGuardianName())
                .guardianPhone(studentRequestDTO.getGuardianPhone())
                .branchId(UUID.randomUUID().toString())  // testing purpose
                .build();
        return null;
    }

    @Override
    public StudentResponseDTO updateStudent(String erpId, StudentRequestDTO studentRequestDTO, String collegeId) {
        return null;
    }

    @Override
    public StudentResponseDTO getStudentByErpId(String erpId, String collegeId) {
        return null;
    }

    @Override
    public StudentResponseDTO deleteStudent(String erpId, String collegeId) {
        return null;
    }

    @Override
    public StudentResponseDTO getAllStudents(String collegeId) {
        return null;
    }
}
