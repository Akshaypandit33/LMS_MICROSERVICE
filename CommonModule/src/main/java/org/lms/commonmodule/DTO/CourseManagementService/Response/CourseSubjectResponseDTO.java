package org.lms.commonmodule.DTO.CourseManagementService.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseSubjectResponseDTO {
    private String title;
    private String type;
    private String subjectCode;
    private String teacher;
    private String description;
    private Integer credits;
    private Boolean isElective;
    private String electiveGroup;
}
