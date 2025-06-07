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
public class CourseCurriculumResponseDTO {
    private UUID id;
    private String curriculumName;
    private String curriculumCode;
    private Long branchId;
    private String academicYear;
    private Integer totalSemesters;
    private Integer totalCredits;
    private List<CurriculumSubjectResponseDTO> subjects;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
