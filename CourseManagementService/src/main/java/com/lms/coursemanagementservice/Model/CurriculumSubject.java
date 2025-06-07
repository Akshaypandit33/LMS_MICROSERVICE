package com.lms.coursemanagementservice.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CurriculumSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    private UUID collegeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id", nullable = false)
    private CourseCurriculum courseCurriculum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_subject_id", nullable = false)
    private BranchSubject branchSubject;

    @Column(name = "sequence_order")
    private Integer sequenceOrder;

    @Column(name = "is_elective", nullable = false)
    private Boolean isElective = false;

    @Column(name = "elective_group")
    private String electiveGroup; // For grouping elective subjects

}
