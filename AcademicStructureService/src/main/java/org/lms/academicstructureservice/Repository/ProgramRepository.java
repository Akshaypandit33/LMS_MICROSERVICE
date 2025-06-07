package org.lms.academicstructureservice.Repository;

import org.hibernate.annotations.NotFound;
import org.lms.academicstructureservice.Model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgramRepository extends JpaRepository<Program, UUID> {

    List<Program> findAllByCollegeId(UUID collegeId);

    Boolean existsByCollegeIdAndProgramCode(UUID collegeId, String programCode);
    Optional<Program> findProgramByCollegeIdAndProgramCode(UUID collegeId, String programCode);
}
