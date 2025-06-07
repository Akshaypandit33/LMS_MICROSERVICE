package org.lms.academicstructureservice.DTOMapper;

import org.lms.academicstructureservice.Model.HOD;
import org.lms.commonmodule.DTO.UserService.HODResponseDTO;
import org.lms.commonmodule.DTO.UserService.Response.FacultyResponseDTO;

public class HODResponseMapper {

    public static HODResponseDTO from(HOD hod, FacultyResponseDTO faculty)
    {
        return HODResponseDTO.builder()
                .userId(String.valueOf(hod.getUserId()))
                .employeeId(faculty.getEmployeeId())
                .email(faculty.getEmail())
                .firstName(faculty.getFirstName())
                .lastName(faculty.getLastName())
                .phoneNumber(faculty.getPhoneNumber())
                .build();
    }
}
