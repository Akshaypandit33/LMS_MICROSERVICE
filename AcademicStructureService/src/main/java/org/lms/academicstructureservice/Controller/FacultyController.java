package org.lms.academicstructureservice.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lms.academicstructureservice.Model.FacultyProfile;
import org.lms.academicstructureservice.Service.FacultyProfileService;
import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.UserService.Request.FacultyRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.FacultyResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyProfileService facultyService;

    /**
     * Create a new faculty member
     * @param facultyRequestDTO Faculty details
     * @param collegeId College ID from header
     * @return Created faculty details
     */
    @PostMapping("/")
    public ResponseEntity<FacultyResponseDTO> createFaculty(
            @Valid @RequestBody FacultyRequestDTO facultyRequestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        FacultyResponseDTO createdFaculty = facultyService.createFaculty(facultyRequestDTO, collegeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaculty);
    }

    /**
     * Update existing faculty member
     * @param userId User ID of the faculty member
     * @param facultyRequestDTO Updated faculty details
     * @param collegeId College ID from header
     * @return Updated faculty details
     */
    @PutMapping("/{userId}")
    public ResponseEntity<FacultyResponseDTO> updateFaculty(
            @PathVariable("userId") String userId,
            @Valid @RequestBody FacultyRequestDTO facultyRequestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        FacultyResponseDTO updatedFaculty = facultyService.updateFaculty(userId, facultyRequestDTO, collegeId);
        return ResponseEntity.ok(updatedFaculty);
    }

    /**
     * Get all faculty members for a college
     * @param collegeId College ID from header
     * @return List of all faculty members
     */
    @GetMapping("/")
    public ResponseEntity<List<FacultyResponseDTO>> getAllFaculty(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        List<FacultyResponseDTO> facultyList = facultyService.getAllFaculty(collegeId);
        return ResponseEntity.ok(facultyList);
    }

    /**
     * Delete a faculty member
     * @param userId User ID of the faculty member to delete
     * @param collegeId College ID from header
     * @return No content response
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteFaculty(
            @PathVariable("userId") String userId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        facultyService.deleteFaculty(userId, collegeId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get faculty member by user ID
     * Note: This endpoint would require a new service method to be implemented
     * @param userId User ID of the faculty member
     * @param collegeId College ID from header
     * @return Faculty details
     */
    @GetMapping("/{userId}")
    public ResponseEntity<FacultyResponseDTO> getFacultyByUserId(
            @PathVariable("userId") String userId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        return ResponseEntity.ok(facultyService.getFacultyByUserId(userId, collegeId));
    }

    /**
     * Get faculty members by department/branch
     * Note: This endpoint would require a new service method to be implemented
     * @param branchCode Branch/Department code
     * @param collegeId College ID from header
     * @return List of faculty members in the department
     */
    @GetMapping("/department/{branchCode}")
    public ResponseEntity<List<FacultyResponseDTO>> getFacultyByDepartment(
            @PathVariable("branchCode") String branchCode,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        // Note: This would require implementing getFacultyByDepartment in the service
        throw new UnsupportedOperationException("getFacultyByDepartment method not implemented in service");
    }


    /**
     * Get all HODs for a college
     * Note: This endpoint would require a new service method to be implemented
     * @param collegeId College ID from header
     * @return List of all HODs
     */
    @GetMapping("/hods")
    public ResponseEntity<List<FacultyResponseDTO>> getAllHODs(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        // Note: This would require implementing getAllHODs in the service
        throw new UnsupportedOperationException("getAllHODs method not implemented in service");
    }
}