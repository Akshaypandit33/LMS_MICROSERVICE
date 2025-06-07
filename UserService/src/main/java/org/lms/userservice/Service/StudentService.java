package org.lms.userservice.Service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.lms.commonmodule.DTO.UserService.Request.StudentRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.StudentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface StudentService {

    /**
     * Creates a new student.

     */
    StudentResponseDTO createStudent(@Valid StudentRequestDTO studentRequestDTO,
                                     @NotNull(message = "College Id cannot be null") String collegeId);

    /**
     * Update an existing student by their ERP ID.
     */
    StudentResponseDTO updateStudent(@NotNull(message = "ERP ID cannot be null") String erpId,
                                         @Valid StudentRequestDTO studentRequestDTO,
                                         @NotNull(message = "College Id cannot be null") String collegeId);

    /**
     * Retrieves a specific student by their ERP ID.
     */
    StudentResponseDTO getStudentByErpId(@NotNull(message = "ERP ID cannot be null") String erpId,
                                             @NotNull(message = "College Id cannot be null") String collegeId);

    /**
     * Deletes a student by their ERP ID.
     */
    StudentResponseDTO deleteStudent(@NotNull(message = "ERP ID cannot be null") String erpId,
                                         @NotNull(message = "College Id cannot be null") String collegeId);

    /**
     * Get a list of all students.
     *
     * @return A tuple of ResponseStatus (SUCCESS, FAILURE) and ResponseEntity (Response body).
     */
    StudentResponseDTO getAllStudents(@NotNull(message = "College Id cannot be null") String collegeId);

}
