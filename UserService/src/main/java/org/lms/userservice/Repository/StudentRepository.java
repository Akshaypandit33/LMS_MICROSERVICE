package org.lms.userservice.Repository;
import org.lms.userservice.Model.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentProfile, UUID> {

    Optional<StudentProfile> findByErpId(String ErpId);
}
