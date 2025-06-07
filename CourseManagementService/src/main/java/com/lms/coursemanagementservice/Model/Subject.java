package com.lms.coursemanagementservice.Model;

import org.lms.commonmodule.Constants.Enum.SubjectType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    private String name;
    private String description;
    private Integer credits;
    private UUID collegeId;
    @Enumerated(EnumType.STRING)
    private SubjectType  subjectType;

    @CreationTimestamp
    private ZonedDateTime createdAt;
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    // One subject can be offered in multiple branches with different codes
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BranchSubject> branchSubjects = new ArrayList<>();
}
