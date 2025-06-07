package com.lms.coursemanagementservice.Controller;


import com.lms.coursemanagementservice.Service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.CourseManagementService.Request.CreateSubjectRequestDTO;
import org.lms.commonmodule.DTO.CourseManagementService.Response.SubjectResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectResponseDTO> createSubject(
            @RequestBody CreateSubjectRequestDTO requestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return ResponseEntity.ok(subjectService.createSubject(requestDTO, collegeId));
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectResponseDTO> getSubjectById(
            @PathVariable UUID subjectId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return ResponseEntity.ok(subjectService.getSubjectById(subjectId, collegeId));
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return ResponseEntity.ok(subjectService.getAllSubjects(collegeId));
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectResponseDTO> updateSubject(
            @PathVariable UUID subjectId,
            @RequestBody CreateSubjectRequestDTO requestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return ResponseEntity.ok(subjectService.updateSubject(subjectId, requestDTO, collegeId));
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> deleteSubject(
            @PathVariable UUID subjectId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        subjectService.deleteSubject(subjectId, collegeId);
        return ResponseEntity.noContent().build();
    }
}

