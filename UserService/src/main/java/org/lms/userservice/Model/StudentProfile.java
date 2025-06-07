package org.lms.userservice.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;
    private String universityRollNo;
    private String erpId;
    private String collegeId;
    private String branchId;
    private String batchYear;
    private String guardianName;
    private String guardianPhone;
    private int currentSem = 1;

    @PrePersist
    @PreUpdate
    public void format()
    {
        this.erpId = erpId.toUpperCase();
    }
}
