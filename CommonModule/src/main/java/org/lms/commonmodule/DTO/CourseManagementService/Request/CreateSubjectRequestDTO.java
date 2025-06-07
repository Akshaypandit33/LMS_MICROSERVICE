package org.lms.commonmodule.DTO.CourseManagementService.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lms.commonmodule.Constants.Enum.SubjectType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubjectRequestDTO {
    @NotBlank(message = "Subject name is required")
    private String name;

    private String description;

    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 10, message = "Credits cannot exceed 10")
    private Integer credits;

    @NotNull(message = "Subject type is required")
    private SubjectType subjectType;

}