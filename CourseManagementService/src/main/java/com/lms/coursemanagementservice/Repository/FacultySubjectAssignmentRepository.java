package com.lms.coursemanagementservice.Repository;

import com.lms.coursemanagementservice.Model.FacultySubjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface FacultySubjectAssignmentRepository extends JpaRepository<FacultySubjectAssignment, UUID> {
}
