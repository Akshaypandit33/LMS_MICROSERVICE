package com.lms.coursemanagementservice.ServiceImpl;

import com.lms.coursemanagementservice.Feign.BranchServiceInterface;
import com.lms.coursemanagementservice.Model.BranchSubject;
import com.lms.coursemanagementservice.Model.Subject;
import com.lms.coursemanagementservice.Repository.BranchSubjectRepository;
import com.lms.coursemanagementservice.Repository.SubjectRepository;
import com.lms.coursemanagementservice.Service.BranchSubjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lms.commonmodule.DTO.CourseManagementService.Request.CreateBranchSubjectRequestDTO;
import org.lms.commonmodule.DTO.CourseManagementService.Response.BranchSubjectResponseDTO;
import org.lms.commonmodule.DTO.CourseManagementService.Response.SubjectResponseDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchSubjectServiceImpl implements BranchSubjectService {

    private final BranchSubjectRepository branchSubjectRepository;
    private final SubjectRepository subjectRepository;
    private final BranchServiceInterface branchService;

    @Override
    @Transactional
    public BranchSubjectResponseDTO createBranchSubject(CreateBranchSubjectRequestDTO requestDTO, String collegeId) {
        Subject subject = subjectRepository.findById(requestDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        boolean exists = Boolean.TRUE.equals(branchService.checkBranch(UUID.fromString(requestDTO.getBranchId())).getBody());
        if(!exists) {
            throw new ResourceNotFoundException("Branch not found "+requestDTO.getBranchId());
        }
        BranchSubject branchSubject = new BranchSubject();
        branchSubject.setSubject(subject);
        branchSubject.setBranchId(UUID.fromString(requestDTO.getBranchId()));
        branchSubject.setCollegeId(UUID.fromString(collegeId));
        branchSubject.setSemester(requestDTO.getSemester());
        branchSubject.setSubjectCode(requestDTO.getSubjectCode());

        BranchSubject saved = branchSubjectRepository.save(branchSubject);
        return mapToResponse(saved);
    }

    @Override
    public BranchSubjectResponseDTO getBranchSubjectById(UUID id, String collegeId) {
        BranchSubject branchSubject = branchSubjectRepository.findBranchSubjectByIdAndCollegeId(id, UUID.fromString(collegeId))
                .orElseThrow(() -> new ResourceNotFoundException("BranchSubject not found"));
        return mapToResponse(branchSubject);
    }

    @Override
    public List<BranchSubjectResponseDTO> getAllBranchSubjects(String collegeId) {
        List<BranchSubject> branchSubjects = branchSubjectRepository.findAllByCollegeId(UUID.fromString(collegeId));
        return branchSubjects.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BranchSubjectResponseDTO updateBranchSubject(UUID id, CreateBranchSubjectRequestDTO requestDTO, String collegeId) {
        BranchSubject branchSubject = branchSubjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BranchSubject not found"));

        Subject subject = subjectRepository.findById(requestDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        branchSubject.setSubject(subject);
        branchSubject.setBranchId(UUID.fromString(requestDTO.getBranchId()));
        branchSubject.setCollegeId(UUID.fromString(collegeId));
        branchSubject.setSemester(requestDTO.getSemester());
        branchSubject.setSubjectCode(requestDTO.getSubjectCode());

        return mapToResponse(branchSubjectRepository.save(branchSubject));
    }

    @Override
    @Transactional
    public void deleteBranchSubject(UUID id, String collegeId) {
        BranchSubject branchSubject = branchSubjectRepository.findBranchSubjectByIdAndCollegeId(id, UUID.fromString(collegeId))
                .orElseThrow(() -> new ResourceNotFoundException("BranchSubject not found"));
        branchSubjectRepository.delete(branchSubject);
    }

    private BranchSubjectResponseDTO mapToResponse(BranchSubject branchSubject) {
        return BranchSubjectResponseDTO.builder()
                .id(branchSubject.getId())
                .branchId(branchSubject.getBranchId().toString())
                .collegeId(branchSubject.getCollegeId().toString())
                .subjectCode(branchSubject.getSubjectCode())
                .semester(branchSubject.getSemester())
                .subject(SubjectResponseDTO.builder()
                        .id(branchSubject.getSubject().getId())
                        .name(branchSubject.getSubject().getName())
                        .description(branchSubject.getSubject().getDescription())
                        .credits(branchSubject.getSubject().getCredits())
                        .subjectType(branchSubject.getSubject().getSubjectType())
                        .createdAt(branchSubject.getSubject().getCreatedAt())
                        .updatedAt(branchSubject.getSubject().getUpdatedAt())
                        .build())
                .createdAt(branchSubject.getCreatedAt())
                .updatedAt(branchSubject.getUpdatedAt())
                .build();
    }
}
