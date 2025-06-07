package com.lms.coursemanagementservice.Repository;

import com.lms.coursemanagementservice.Model.BranchSubject;
import org.lms.commonmodule.DTO.CourseManagementService.Response.BranchSubjectResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BranchSubjectRepository extends JpaRepository<BranchSubject, UUID> {

    Optional<BranchSubject> findBranchSubjectByIdAndCollegeId(UUID id, UUID collegeId);
    Optional<BranchSubject> findBranchSubjectByBranchIdAndCollegeId(UUID branchId, UUID collegeId);
    List<BranchSubject> findAllByBranchIdAndCollegeId(UUID branchId, UUID collegeId);
    List<BranchSubject> findAllByCollegeId(UUID collegeId);
    void removeBranchSubjectsByBranchIdAndCollegeId(UUID branchId, UUID collegeId);
}
