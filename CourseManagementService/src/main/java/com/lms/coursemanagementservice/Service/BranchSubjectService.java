package com.lms.coursemanagementservice.Service;

import org.lms.commonmodule.DTO.CourseManagementService.Request.CreateBranchSubjectRequestDTO;
import org.lms.commonmodule.DTO.CourseManagementService.Response.BranchSubjectResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BranchSubjectService {

    BranchSubjectResponseDTO createBranchSubject(CreateBranchSubjectRequestDTO requestDTO, String collegeId);

    BranchSubjectResponseDTO getBranchSubjectById(UUID id, String collegeId);

    List<BranchSubjectResponseDTO> getAllBranchSubjects(String collegeId);

    BranchSubjectResponseDTO updateBranchSubject(UUID id, CreateBranchSubjectRequestDTO requestDTO, String collegeId);

    void deleteBranchSubject(UUID id, String collegeId);
}

