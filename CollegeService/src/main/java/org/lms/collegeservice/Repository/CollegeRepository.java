package org.lms.collegeservice.Repository;

import org.lms.collegeservice.Model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollegeRepository extends JpaRepository<College, UUID> {

    Optional<College> getCollegeByCollegeCode(String collegeCode);
    Boolean existsCollegeByCollegeCode(String collegeCode);
}
