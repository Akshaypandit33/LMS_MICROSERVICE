package com.lms.coursemanagementservice.Controller;

import com.lms.coursemanagementservice.Service.BranchSubjectService;
import lombok.RequiredArgsConstructor;
import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.CourseManagementService.Request.CreateBranchSubjectRequestDTO;
import org.lms.commonmodule.DTO.CourseManagementService.Response.BranchSubjectResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/branch-subjects")
@RequiredArgsConstructor
public class BranchSubjectController {

    private final BranchSubjectService branchSubjectService;

    @PostMapping
    public ResponseEntity<BranchSubjectResponseDTO> createBranchSubject(
            @RequestBody CreateBranchSubjectRequestDTO requestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        BranchSubjectResponseDTO response = branchSubjectService.createBranchSubject(requestDTO, collegeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchSubjectResponseDTO> getBranchSubjectById(
            @PathVariable UUID id,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        BranchSubjectResponseDTO response = branchSubjectService.getBranchSubjectById(id, collegeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BranchSubjectResponseDTO>> getAllBranchSubjects(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        List<BranchSubjectResponseDTO> response = branchSubjectService.getAllBranchSubjects(collegeId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchSubjectResponseDTO> updateBranchSubject(
            @PathVariable UUID id,
            @RequestBody CreateBranchSubjectRequestDTO requestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        BranchSubjectResponseDTO response = branchSubjectService.updateBranchSubject(id, requestDTO, collegeId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranchSubject(
            @PathVariable UUID id,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        branchSubjectService.deleteBranchSubject(id, collegeId);
        return ResponseEntity.noContent().build();
    }
}
