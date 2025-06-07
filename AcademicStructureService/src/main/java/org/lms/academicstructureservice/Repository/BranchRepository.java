package org.lms.academicstructureservice.Repository;

import org.lms.academicstructureservice.Model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {

    // Basic CRUD operations by college
    List<Branch> findByDepartment_Program_CollegeId(UUID collegeId);

    Optional<Branch> findByIdAndDepartment_Program_CollegeId(UUID branchId, UUID collegeId);

    void deleteByIdAndDepartment_Program_CollegeId(UUID branchId, UUID collegeId);

    // Find by department
    List<Branch> findByDepartmentIdAndDepartment_Program_CollegeId(UUID departmentId, UUID collegeId);

    // Find by program
    List<Branch> findByDepartment_Program_IdAndDepartment_Program_CollegeId(UUID programId, UUID collegeId);

    // Check existence by branch code
    boolean existsByCodeAndDepartment_Program_CollegeId(String branchCode,UUID collegeId);

    // Check existence by branch code excluding current branch (for updates)
    boolean existsByCodeAndDepartment_Program_CollegeIdAndIdNot(String branchCode, UUID collegeId, UUID branchId);

    // Search operations
    @Query("SELECT b FROM Branch b WHERE b.department.program.collegeId = :collegeId AND " +
            "(LOWER(b.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.code) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Branch> searchBranches(@Param("searchTerm") String searchTerm,
                                           @Param("collegeId") String collegeId);

    // Count operations
    long countByDepartment_Program_CollegeId(UUID collegeId);

    long countByDepartmentIdAndDepartment_Program_CollegeId(UUID departmentId, UUID collegeId);


    // Advanced search with department filter
    @Query("SELECT b FROM Branch b WHERE b.department.program.collegeId = :collegeId AND b.department.id = :departmentId AND " +
            "(LOWER(b.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.code) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Branch> searchBranchesByDepartment(@Param("searchTerm") String searchTerm,
                                            @Param("departmentId") UUID departmentId,
                                            @Param("collegeId") UUID collegeId);


    List<Branch> findByDepartment_Program_CollegeIdOrderByNameAsc(UUID collegeId);

    Boolean existsBranchByIdAndDepartment_Program_CollegeId(UUID branchId, UUID collegeId);
}
