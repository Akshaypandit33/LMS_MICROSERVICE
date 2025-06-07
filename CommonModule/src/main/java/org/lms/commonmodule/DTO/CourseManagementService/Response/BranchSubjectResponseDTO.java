package org.lms.commonmodule.DTO.CourseManagementService.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchSubjectResponseDTO {
    private UUID id;
    private SubjectResponseDTO subject;
    private String branchId;
    private String subjectCode;
    private String collegeId;
    private Integer semester;
    private List<FacultyAssignmentResponseDTO> facultyAssignments;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
