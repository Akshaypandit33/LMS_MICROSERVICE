package org.lms.academicstructureservice.ServiceImpl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lms.academicstructureservice.DTOMapper.HODResponseMapper;
import org.lms.academicstructureservice.Feign.UserRoleInterface;
import org.lms.academicstructureservice.Model.Branch;
import org.lms.academicstructureservice.Model.HOD;
import org.lms.academicstructureservice.Repository.BranchRepository;
import org.lms.academicstructureservice.Repository.HODRepository;
import org.lms.academicstructureservice.Service.FacultyProfileService;
import org.lms.academicstructureservice.Service.HODService;
import org.lms.commonmodule.DTO.UserService.HODResponseDTO;
import org.lms.commonmodule.DTO.UserService.Response.FacultyResponseDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions.BranchNotFoundException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HODServiceImpl implements HODService {

    private final HODRepository hodRepository;
    private final BranchRepository branchRepository;
    private final FacultyProfileService facultyProfileService;
    private final UserRoleInterface userRoleInterface;
    @Override
    @Transactional
    public HODResponseDTO assignHod(UUID userId, UUID branchId, UUID assignedBy, UUID collegeId) {
        log.debug("Attempting to assign HOD: userId={}, branchId={}", userId, branchId);

        // Validate that user is not already HOD of any branch
        if (hodRepository.existsActiveHodByUserId(userId)) {
            throw new BusinessException("User is already HOD of another branch");
        }

        // Validate that branch doesn't already have HOD
        if (hodRepository.existsActiveHodByBranchId(branchId)) {
            throw new BusinessException("Branch already has an active HOD");
        }

        // Validate branch exists
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found"));

        // Create new HOD assignment
        HOD hod = HOD.builder()
                .userId(userId)
                .branch(branch)
                .assignedBy(assignedBy)
                .collegeId(collegeId)
                .isActive(true)
                .build();

        HOD savedHod = hodRepository.save(hod);
        userRoleInterface.createUserRole(savedHod.getUserId(),"HOD");
        log.info("HOD assigned successfully: userId={}, branchId={}", userId, branchId);
       FacultyResponseDTO facultyResponseDTO = facultyProfileService.getFacultyByUserId(String.valueOf(savedHod.getUserId()),String.valueOf(collegeId));
        return HODResponseMapper.from(savedHod, facultyResponseDTO);
    }

    @Override
    @Transactional
    public void removeHod(UUID branchId, UUID removedBy, UUID collegeId) {
        log.debug("Attempting to remove HOD from branch: branchId={}", branchId);

        HOD hod = hodRepository.findActiveHodByBranchId(branchId,collegeId)
                .orElseThrow(() -> new BusinessException("No active HOD found for this branch"));

        // Soft delete - mark as inactive
        hod.setIsActive(false);
        hodRepository.save(hod);
        userRoleInterface.removeUserRole(hod.getUserId(),"HOD");
        log.info("HOD removed successfully from branch: branchId={}, hodUserId={}",
                branchId, hod.getUserId());
    }

    @Override
    @Transactional
    public HODResponseDTO transferHod(UUID currentBranchId, UUID newBranchId, UUID transferredBy,UUID collegeId) {
        log.debug("Attempting to transfer HOD from branch {} to branch {}",
                currentBranchId, newBranchId);

        // Get current HOD
        HOD currentHod = hodRepository.findActiveHodByBranchId(currentBranchId,collegeId)
                .orElseThrow(() -> new BusinessException("No active HOD found for current branch"));

        // Validate new branch doesn't have HOD
        if (hodRepository.existsActiveHodByBranchId(newBranchId)) {
            throw new BusinessException("Target branch already has an active HOD");
        }

        // Validate new branch exists
        Branch newBranch = branchRepository.findById(newBranchId)
                .orElseThrow(() -> new BusinessException("Target branch not found"));

        // Remove from current branch
        currentHod.setIsActive(false);
        hodRepository.save(currentHod);
        userRoleInterface.removeUserRole(currentHod.getUserId(),"HOD");
        // Assign to new branch
        return assignHod(currentHod.getUserId(), newBranchId, transferredBy, currentHod.getCollegeId());
    }

    @Override
    public Optional<HODResponseDTO> getHodByBranch(UUID branchId,UUID collegeId) {
        HOD hod = hodRepository.findActiveHodByBranchId(branchId,collegeId).orElseThrow(
                () -> new BusinessException("No active HOD found for this branch")
        );
        FacultyResponseDTO facultyResponseDTO = facultyProfileService.getFacultyByUserId(String.valueOf(hod.getUserId()),String.valueOf(collegeId));
        return Optional.ofNullable(HODResponseMapper.from(hod, facultyResponseDTO));

    }

    @Override
    public Optional<HODResponseDTO> getHodByUser(UUID userId, UUID collegeId) {
        HOD hod = hodRepository.findActiveHodByUserId(userId,collegeId).orElseThrow(
                () -> new BusinessException("No active HOD found for this user")
        );
        FacultyResponseDTO facultyResponseDTO = facultyProfileService.getFacultyByUserId(String.valueOf(hod.getUserId()),String.valueOf(collegeId));
        return Optional.ofNullable(HODResponseMapper.from(hod, facultyResponseDTO));
    }

    @Override
    public boolean isUserHod(UUID userId) {
        return hodRepository.existsActiveHodByUserId(userId);
    }

    @Override
    public boolean branchHasHod(UUID branchId) {
        return hodRepository.existsActiveHodByBranchId(branchId);
    }
}
