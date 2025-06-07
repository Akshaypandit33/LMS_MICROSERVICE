package org.lms.academicstructureservice.Service;




import org.lms.commonmodule.DTO.AcademicStructureService.Request.BranchRequestDTO;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.BranchResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BranchService {

    BranchResponseDTO createBranch(BranchRequestDTO requestDTO, String collegeId);
    BranchResponseDTO getBranchById(UUID branchId, String collegeId);
    BranchResponseDTO getBranchByCode(String branchCode, String collegeId);
    List<BranchResponseDTO> getAllBranches(String collegeId);
    List<BranchResponseDTO> getBranchesByDepartmentId(UUID departmentId, String collegeId);
    List<BranchResponseDTO> getBranchesByProgramId(UUID programId, String collegeId);
    BranchResponseDTO updateBranch(UUID branchId, BranchRequestDTO requestDTO, String collegeId);
    void deleteBranch(UUID branchId, String collegeId);
    boolean existsByBranchCode(String branchCode, String collegeId);
    List<BranchResponseDTO> searchBranches(String searchTerm, String collegeId);
    long getBranchCount(String collegeId);
    long getBranchCountByDepartment(UUID departmentId, String collegeId);
    Boolean existsBranchByBranchId(UUID branchId);

}
