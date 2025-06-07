package org.lms.academicstructureservice.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.lms.academicstructureservice.Service.DepartmentService;
import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.AcademicStructureService.Request.DepartmentRequestDTO;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.DepartmentResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@Validated
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class DepartmentController {

    private final DepartmentService departmentService;
//    private Logger log = LoggerFactory.getLogger(DepartmentController.class);
    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId,
            @Valid @RequestBody DepartmentRequestDTO requestDTO) {

        log.info("Creating new department for college: {}", collegeId);
        DepartmentResponseDTO response = departmentService.createDepartment(requestDTO, collegeId);
        log.info("Department created successfully with ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId,
            @PathVariable @NotNull UUID departmentId) {

        log.info("Fetching department: {} for college: {}", departmentId, collegeId);
        DepartmentResponseDTO response = departmentService.getDepartmentById(departmentId, collegeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        log.info("Fetching all departments for college: {}", collegeId);
        List<DepartmentResponseDTO> departments = departmentService.getAllDepartments(collegeId);
        log.info("Found {} departments for college: {}", departments.size(), collegeId);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/by-program/{programCode}")
    public ResponseEntity<List<DepartmentResponseDTO>> getDepartmentsByProgram(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId,
            @PathVariable @NotNull String programCode) {

        log.info("Fetching departments for program: {} in college: {}", programCode, collegeId);
        List<DepartmentResponseDTO> departments = departmentService.getDepartmentsByProgramId(programCode, collegeId);
        log.info("Found {} departments for program: {}", departments.size(), programCode);
        return ResponseEntity.ok(departments);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId,
            @PathVariable @NotNull UUID departmentId,
            @Valid @RequestBody DepartmentRequestDTO requestDTO) {

        log.info("Updating department: {} for college: {}", departmentId, collegeId);
        DepartmentResponseDTO response = departmentService.updateDepartment(departmentId, requestDTO, collegeId);
        log.info("Department updated successfully: {}", departmentId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId,
            @PathVariable @NotNull UUID departmentId) {

        log.info("Deleting department: {} for college: {}", departmentId, collegeId);
        departmentService.deleteDepartment(departmentId, collegeId);
        log.info("Department deleted successfully: {}", departmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{departmentCode}")
    public ResponseEntity<Boolean> checkDepartmentCodeExists(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId,
            @PathVariable @NotBlank String departmentCode) {

        log.info("Checking existence of department code: {} for college: {}", departmentCode, collegeId);
        boolean exists = departmentService.existsByDepartmentCode(departmentCode, collegeId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DepartmentResponseDTO>> searchDepartments(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId,
            @RequestParam @NotBlank String searchTerm) {

        log.info("Searching departments with term: '{}' for college: {}", searchTerm, collegeId);
        List<DepartmentResponseDTO> departments = departmentService.searchDepartments(searchTerm, collegeId);
        log.info("Found {} departments matching search term: '{}'", departments.size(), searchTerm);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getDepartmentCount(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        log.info("Getting department count for college: {}", collegeId);
        long count = departmentService.getDepartmentCount(collegeId);
        log.info("Department count for college {}: {}", collegeId, count);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Department service is running");
    }
}