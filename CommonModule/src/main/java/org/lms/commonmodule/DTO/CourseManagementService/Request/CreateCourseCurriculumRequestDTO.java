package org.lms.commonmodule.DTO.CourseManagementService.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseCurriculumRequestDTO {
    @NotBlank(message = "Curriculum name is required")
    private String curriculumName;

    @NotBlank(message = "Curriculum code is required")
    private String curriculumCode;

    @NotNull(message = "Branch ID is required")
    private Long branchId;

    @NotBlank(message = "Academic year is required")
    private String academicYear;

    @Min(value = 1, message = "Total semesters must be at least 1")
    @Max(value = 10, message = "Total semesters cannot exceed 10")
    private Integer totalSemesters;

    @Min(value = 1, message = "Total credits must be at least 1")
    private Integer totalCredits;

    @Valid
    private List<CurriculumSubjectRequestDTO> subjects = new ArrayList<>();
}
