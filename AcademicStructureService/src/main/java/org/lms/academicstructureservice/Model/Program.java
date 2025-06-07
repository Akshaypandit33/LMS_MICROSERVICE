package org.lms.academicstructureservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "programs", indexes = {
        @Index(name = "idx_program_name", columnList = "name"),
        @Index(name = "idx_program_code", columnList = "programCode", unique = true)
})
@Builder
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String programCode;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer durationYears;

    private UUID collegeId;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;


    @PrePersist
    @PreUpdate
    public void format()
    {
        this.programCode = programCode.toUpperCase();
    }
}
