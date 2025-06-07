package org.lms.academicstructureservice.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "hods", indexes = {
        @Index(name = "idx_hod_user_id", columnList = "userId", unique = true),
        @Index(name = "idx_hod_branch_id", columnList = "branch_id", unique = true)
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HOD {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, name = "user_id")
    private UUID userId; // One user can be HOD of only one branch

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, unique = true)
    private Branch branch; // One branch can have only one HOD

    @Column(name = "college_id", nullable = false)
    private UUID collegeId;

    @Column(name = "assigned_by", nullable = false)
    private UUID assignedBy;

    @CreationTimestamp
    @Column(name = "assigned_on")
    private ZonedDateTime assignedOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    // Helper methods
    public boolean isActiveHod() {
        return Boolean.TRUE.equals(this.isActive);
    }
}