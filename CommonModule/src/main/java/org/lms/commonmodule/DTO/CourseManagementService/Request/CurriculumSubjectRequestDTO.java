package org.lms.commonmodule.DTO.CourseManagementService.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

// Curriculum subject request
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumSubjectRequestDTO {
    @NotNull(message = "Branch subject ID is required")
    private UUID branchSubjectId;

    private Integer sequenceOrder;

    private Boolean isElective = false;

    private String electiveGroup;
}