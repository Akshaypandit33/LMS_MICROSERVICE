package org.lms.commonmodule.DTO.CourseManagementService.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class CreateBranchSubjectRequestDTO {
    @NotNull(message = "Subject ID is required")
    private UUID subjectId;

    @NotBlank(message = "Branch ID is required")
    private String branchId;

    @NotBlank(message = "Subject code is required")
    private String subjectCode;

    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 10, message = "Semester cannot exceed 8")
    private Integer semester;
}