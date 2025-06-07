package com.lms.coursemanagementservice.Repository;

import com.lms.coursemanagementservice.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    Optional<Subject> findSubjectByIdAndCollegeId(UUID subjectId, UUID collegeId);
    List<Subject> findAllByCollegeId(UUID collegeId);

    void removeSubjectByIdAndCollegeId(UUID id, UUID collegeId);
}
