package org.lms.academicstructureservice.Service;

import org.lms.commonmodule.DTO.AcademicStructureService.Request.DepartmentRequestDTO;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.DepartmentResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    DepartmentResponseDTO createDepartment(DepartmentRequestDTO requestDTO, String collegeId);
    DepartmentResponseDTO getDepartmentById(UUID departmentId, String collegeId);
    List<DepartmentResponseDTO> getAllDepartments(String collegeId);
    List<DepartmentResponseDTO> getDepartmentsByProgramId(String programCode, String collegeId);
    DepartmentResponseDTO updateDepartment(UUID departmentId, DepartmentRequestDTO requestDTO, String collegeId);
    void deleteDepartment(UUID departmentId, String collegeId);
    boolean existsByDepartmentCode(String departmentCode, String collegeId);
    List<DepartmentResponseDTO> searchDepartments(String searchTerm, String collegeId);
    long getDepartmentCount(String collegeId);
}
