package org.lms.academicstructureservice.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "branches", indexes = {
        @Index(name = "idx_branch_name", columnList = "name"),
        @Index(name = "idx_branch_code", columnList = "code", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // Bidirectional relationship with HOD
    @OneToOne(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private HOD hod;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void format() {
        this.code = code.toUpperCase();
    }

    // Helper method to check if branch has HOD
    public boolean hasHod() {
        return this.hod != null;
    }

    // Helper method to get HOD user ID
    public UUID getHodUserId() {
        return this.hod != null ? this.hod.getUserId() : null;
    }
}