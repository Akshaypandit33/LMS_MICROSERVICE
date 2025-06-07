package com.lms.coursemanagementservice.Service;

import org.lms.commonmodule.DTO.CourseManagementService.Request.CreateSubjectRequestDTO;
import org.lms.commonmodule.DTO.CourseManagementService.Response.SubjectResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SubjectService {
    SubjectResponseDTO createSubject(CreateSubjectRequestDTO requestDTO, String collegeId);

    SubjectResponseDTO getSubjectById(UUID subjectId, String collegeId);

    List<SubjectResponseDTO> getAllSubjects(String collegeId);

    SubjectResponseDTO updateSubject(UUID subjectId, CreateSubjectRequestDTO requestDTO, String collegeId);

    void deleteSubject(UUID subjectId, String collegeId);
}
