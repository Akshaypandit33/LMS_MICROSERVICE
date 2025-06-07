package org.lms.commonmodule.DTO.CourseManagementService.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Complete course structure request (like your example)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseStructureRequestDTO {
    @NotBlank(message = "Course name is required")
    private String course;

    @NotBlank(message = "Branch name is required")
    private String branch;

    @NotBlank(message = "College ID is required")
    private String collegeId;

    @Min(value = 1, message = "Semester must be at least 1")
    private Integer semester;

    @Valid
    @NotEmpty(message = "Subjects list cannot be empty")
    private List<CourseSubjectRequestDTO> subjects;
}

