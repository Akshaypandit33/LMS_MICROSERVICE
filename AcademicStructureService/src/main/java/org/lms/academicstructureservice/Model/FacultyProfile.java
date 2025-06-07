package org.lms.academicstructureservice.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class FacultyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String userId; // FK to User
    private String employeeId;
    private String collegeId;
    private String designation;
    private String qualification;
    private String specialization;
}
