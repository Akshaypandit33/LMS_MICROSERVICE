package org.lms.academicstructureservice.Repository;

import org.lms.academicstructureservice.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    List<Department> findAllByProgram_CollegeId(UUID collegeId);
    Optional<Department> findByIdAndProgram_CollegeId(UUID departmentId, UUID collegeId);

    List<Department> findDepartmentsByProgram_IdAndProgram_CollegeId(UUID programId,UUID collegeId);
    @Query("SELECT d FROM Department d WHERE d.program.collegeId = :collegeId AND " +
            "(LOWER(d.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.code) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Department> findByCollegeIdAndNameContainingIgnoreCaseOrDepartmentCodeContainingIgnoreCase(
            @Param("collegeId") UUID collegeId,
            @Param("searchTerm") String searchTerm);

    boolean existsDepartmentByCodeAndProgram_CollegeId(String departmentCode, UUID collegeId);
    long countAllByProgram_CollegeId(UUID collegeId);

    List<Department> findDepartmentsByProgram_ProgramCodeAndProgram_CollegeId(String programCode, UUID collegeId);
}
