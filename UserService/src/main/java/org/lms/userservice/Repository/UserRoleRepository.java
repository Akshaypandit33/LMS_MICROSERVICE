package org.lms.userservice.Repository;

import jakarta.transaction.Transactional;
import org.lms.userservice.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    Boolean existsByUserIdAndRoleId(UUID userId, UUID roleId);
    List<UserRole> getUserRolesByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserRole ur WHERE ur.user.id = :userId AND ur.role.id = :roleId")
    void deleteUserRole(@Param("userId") UUID userId, @Param("roleId") UUID roleId);

   Optional<UserRole>  findByUserIdAndRoleId(UUID userId, UUID roleId);
   @Query("SELECT c FROM UserRole c WHERE c.user.collegeId = :collegeId")
   List<UserRole> getUserRolesByCollege(@Param("collegeId") UUID collegeId);

   Boolean existsByUserId(UUID userId);
}
