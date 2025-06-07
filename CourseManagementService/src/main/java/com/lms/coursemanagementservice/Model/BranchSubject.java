package com.lms.coursemanagementservice.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"college_id", "branch_id", "subject_code"}),
        @UniqueConstraint(columnNames = {"college_id", "branch_id", "subject_id", "semester"})
})
public class BranchSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "branch_id")
    private UUID branchId;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "college_id")
    private UUID collegeId;

    private Integer semester;

    // One branch subject can have multiple faculty assignments
    @OneToMany(mappedBy = "branchSubject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FacultySubjectAssignment> facultyAssignments;

    @CreationTimestamp
    private ZonedDateTime createdAt;
    @UpdateTimestamp
    private ZonedDateTime updatedAt;



}
