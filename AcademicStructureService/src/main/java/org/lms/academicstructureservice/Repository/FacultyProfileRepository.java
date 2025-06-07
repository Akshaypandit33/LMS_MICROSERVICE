package org.lms.academicstructureservice.Repository;

import lombok.NonNull;

import org.lms.academicstructureservice.Model.FacultyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FacultyProfileRepository extends JpaRepository<FacultyProfile, UUID> {
    List<FacultyProfile> getFacultyProfilesByCollegeId(@NonNull String collegeId);

    void deleteFacultyProfileByUserIdAndCollegeId(@NonNull String userId, @NonNull String collegeId);
    Optional<FacultyProfile> getFacultyProfileByUserIdAndCollegeId(@NonNull String userId, @NonNull String collegeId);
}
