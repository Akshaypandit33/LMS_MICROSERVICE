package com.lms.coursemanagementservice.Repository;

import com.lms.coursemanagementservice.Model.CourseCurriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseCurriculumRepository extends JpaRepository<CourseCurriculum, UUID> {
}
