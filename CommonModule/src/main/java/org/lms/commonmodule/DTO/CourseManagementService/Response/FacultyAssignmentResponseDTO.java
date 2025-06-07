package org.lms.commonmodule.DTO.CourseManagementService.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacultyAssignmentResponseDTO {
    private UUID id;
    private String facultyUserId;
    private String facultyName; // This would come from user service
    private Boolean isCoordinator;
}
