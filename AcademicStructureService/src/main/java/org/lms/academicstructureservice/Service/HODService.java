package org.lms.academicstructureservice.Service;

import org.lms.academicstructureservice.Model.HOD;
import org.lms.commonmodule.DTO.UserService.HODResponseDTO;

import java.util.Optional;
import java.util.UUID;

public interface HODService {
    HODResponseDTO assignHod(UUID userId, UUID branchId, UUID assignedBy, UUID collegeId);
    void removeHod(UUID branchId, UUID removedBy,UUID collegeId);
    HODResponseDTO transferHod(UUID currentBranchId, UUID newBranchId, UUID transferredBy,UUID collegeId);
    Optional<HODResponseDTO> getHodByBranch(UUID branchId, UUID collegeId);
    Optional<HODResponseDTO> getHodByUser(UUID userId,UUID collegeId);
    boolean isUserHod(UUID userId);
    boolean branchHasHod(UUID branchId);
}
