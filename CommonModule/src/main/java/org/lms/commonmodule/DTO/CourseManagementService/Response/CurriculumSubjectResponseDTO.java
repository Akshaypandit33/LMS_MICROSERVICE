package org.lms.commonmodule.DTO.CourseManagementService.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumSubjectResponseDTO {
    private UUID id;
    private BranchSubjectResponseDTO branchSubject;
    private Integer sequenceOrder;
    private Boolean isElective;
    private String electiveGroup;
}