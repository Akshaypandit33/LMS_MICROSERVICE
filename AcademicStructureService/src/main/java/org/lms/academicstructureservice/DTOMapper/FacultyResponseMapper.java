package org.lms.academicstructureservice.DTOMapper;

import lombok.NonNull;
import org.lms.academicstructureservice.Model.FacultyProfile;
import org.lms.commonmodule.DTO.UserService.Response.FacultyResponseDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;

public class FacultyResponseMapper {
    public static FacultyResponseDTO from(@NonNull FacultyProfile facultyProfile, UserResponseDTO userResponseDTO) {
        return FacultyResponseDTO.builder()
                .facultyId(String.valueOf(facultyProfile.getId()))
                .userId(String.valueOf(userResponseDTO.getUserId()))
                .firstName(userResponseDTO.getFirstName())
                .lastName(userResponseDTO.getLastName())
                .phoneNumber(userResponseDTO.getPhoneNumber())
                .email(userResponseDTO.getEmail())
                .collegeId(facultyProfile.getCollegeId())
                .employeeId(facultyProfile.getEmployeeId())
                .designation(facultyProfile.getDesignation())
                .specialization(facultyProfile.getSpecialization())
                .qualification(facultyProfile.getQualification())
                .build();
    }
}
