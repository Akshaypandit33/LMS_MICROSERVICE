package com.lms.coursemanagementservice.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"college_id", "branch_subject_id", "faculty_user_id"})
})
@Builder
public class FacultySubjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_subject_id", nullable = false)
    private BranchSubject branchSubject;

    @Column(name = "faculty_user_id")
    private String facultyUserId;

    private UUID collegeId;

    @Column(name = "is_coordinator", nullable = false)
    private Boolean isCoordinator = false;

}
