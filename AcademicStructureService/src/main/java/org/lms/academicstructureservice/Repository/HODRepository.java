package org.lms.academicstructureservice.Repository;

import org.lms.academicstructureservice.Model.HOD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface HODRepository extends JpaRepository<HOD, UUID> {
    Optional<HOD> findByUserId(UUID userId);

    Optional<HOD> findByBranchId(UUID branchId);

    @Query("SELECT h FROM HOD h WHERE h.userId = :userId AND h.isActive = true AND h.collegeId = :collegeId")
    Optional<HOD> findActiveHodByUserId(@Param("userId") UUID userId,  @Param("collegeId") UUID collegeId);

    @Query("SELECT h FROM HOD h WHERE h.branch.id = :branchId AND h.isActive = true AND h.collegeId = :collegeId")
    Optional<HOD> findActiveHodByBranchId(@Param("branchId") UUID branchId, @Param("collegeId") UUID collegeId);

    boolean existsByUserId(UUID userId);

    boolean existsByBranchId(UUID branchId);

    @Query("SELECT COUNT(h) > 0 FROM HOD h WHERE h.userId = :userId AND h.isActive = true")
    boolean existsActiveHodByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(h) > 0 FROM HOD h WHERE h.branch.id = :branchId AND h.isActive = true")
    boolean existsActiveHodByBranchId(@Param("branchId") UUID branchId);

}
