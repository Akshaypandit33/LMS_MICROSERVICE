package com.lms.coursemanagementservice.ServiceImpl;

import com.lms.coursemanagementservice.Model.Subject;
import com.lms.coursemanagementservice.Repository.SubjectRepository;
import com.lms.coursemanagementservice.Service.SubjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lms.commonmodule.DTO.CourseManagementService.Request.CreateSubjectRequestDTO;
import org.lms.commonmodule.DTO.CourseManagementService.Response.SubjectResponseDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Transactional
    @Override
    public SubjectResponseDTO createSubject(CreateSubjectRequestDTO requestDTO, String collegeId) {
        Subject subject = new Subject();
        subject.setName(requestDTO.getName());
        subject.setDescription(requestDTO.getDescription());
        subject.setCredits(requestDTO.getCredits());
        subject.setSubjectType(requestDTO.getSubjectType());
        subject.setCollegeId(UUID.fromString(collegeId));
        Subject saved = subjectRepository.save(subject);
        return mapToResponse(saved);
    }

    @Override
    public SubjectResponseDTO getSubjectById(UUID subjectId, String collegeId) {
        Subject subject = subjectRepository.findSubjectByIdAndCollegeId(subjectId, UUID.fromString(collegeId))
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        return mapToResponse(subject);
    }

    @Override
    public List<SubjectResponseDTO> getAllSubjects(String collegeId) {
        return subjectRepository.findAllByCollegeId(UUID.fromString(collegeId)).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SubjectResponseDTO updateSubject(UUID subjectId, CreateSubjectRequestDTO requestDTO, String collegeId) {
        Subject subject = subjectRepository.findSubjectByIdAndCollegeId(subjectId,UUID.fromString(collegeId))
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if(requestDTO.getName()!= null) subject.setName(requestDTO.getName());
        if(requestDTO.getDescription()!= null) subject.setDescription(requestDTO.getDescription());
        if(requestDTO.getCredits()!= null) subject.setCredits(requestDTO.getCredits());
        if(requestDTO.getSubjectType()!= null)  subject.setSubjectType(requestDTO.getSubjectType());

        return mapToResponse(subjectRepository.save(subject));
    }

    @Transactional
    @Override
    public void deleteSubject(UUID subjectId, String collegeId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new RuntimeException("Subject not found");
        }
        subjectRepository.removeSubjectByIdAndCollegeId(subjectId,UUID.fromString(collegeId));
    }

    public SubjectResponseDTO mapToResponse(Subject subject) {
        return SubjectResponseDTO.builder()
                .id(subject.getId())
                .name(subject.getName())
                .description(subject.getDescription())
                .credits(subject.getCredits())
                .subjectType(subject.getSubjectType())
                .createdAt(subject.getCreatedAt())
                .updatedAt(subject.getUpdatedAt())
                .build();
    }
}
