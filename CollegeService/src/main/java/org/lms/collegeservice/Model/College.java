package org.lms.collegeservice.Model;

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
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String collegeCode;
    private String logoUrl;
    private String collegeName;
    private String email;
    private String phoneNo;
    private String address;
    private UUID adminId;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void normalizeName() {
        if (this.collegeCode != null) {
            this.collegeCode = this.collegeCode.toUpperCase();
        }
    }
}
