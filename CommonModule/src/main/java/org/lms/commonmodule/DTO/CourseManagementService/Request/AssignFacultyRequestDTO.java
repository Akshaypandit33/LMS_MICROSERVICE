package org.lms.commonmodule.DTO.CourseManagementService.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignFacultyRequestDTO {
    @NotNull(message = "Branch subject ID is required")
    private UUID branchSubjectId;

    @NotBlank(message = "Faculty user ID is required")
    private String facultyUserId;

    private Boolean isCoordinator = false;
}