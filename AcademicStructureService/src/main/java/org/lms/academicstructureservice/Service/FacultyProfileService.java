package org.lms.academicstructureservice.Service;

import lombok.NonNull;
import org.lms.commonmodule.DTO.UserService.Request.FacultyRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.FacultyResponseDTO;

import java.util.List;

public interface FacultyProfileService {
    FacultyResponseDTO createFaculty(@NonNull FacultyRequestDTO facultyResponseDTO, String collegeId);
    FacultyResponseDTO updateFaculty(String userId,@NonNull FacultyRequestDTO facultyRequestDTO, String collegeId);
    List<FacultyResponseDTO> getAllFaculty(@NonNull String CollegeId);

    void deleteFaculty(@NonNull String userId,@NonNull String collegeId);
    FacultyResponseDTO getFacultyByUserId(String userId,@NonNull String CollegeId);
}
