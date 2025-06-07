package com.lms.coursemanagementservice.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CourseCurriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(name = "curriculum_name", nullable = false)
    private String curriculumName;

    @Column(name = "curriculum_code", nullable = false)
    private String curriculumCode;

    @Column(name = "branch_id", nullable = false)
    private UUID branchId;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "total_semesters", nullable = false)
    private Integer totalSemesters;

    @Column(name = "total_credits", nullable = false)
    private Integer totalCredits;

    private UUID collegeId;


    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void prePersistUpdate() {
        this.curriculumCode = curriculumCode.toUpperCase();
    }

}
