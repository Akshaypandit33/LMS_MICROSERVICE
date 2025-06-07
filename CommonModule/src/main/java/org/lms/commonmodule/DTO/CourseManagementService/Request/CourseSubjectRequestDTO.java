package org.lms.commonmodule.DTO.CourseManagementService.Request;
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
public class CourseSubjectRequestDTO {
    @NotBlank(message = "Subject title is required")
    private String title;

    @NotNull(message = "Subject type is required")
    private SubjectType type;

    @NotBlank(message = "Subject code is required")
    private String subjectCode;

    @NotBlank(message = "Teacher name is required")
    private String teacher;

    private String description;

    @Min(value = 1, message = "Credits must be at least 1")
    private Integer credits = 3; // Default credits

    private Boolean isElective = false;

    private String electiveGroup;
}