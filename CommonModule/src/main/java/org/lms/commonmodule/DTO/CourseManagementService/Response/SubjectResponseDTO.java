package org.lms.commonmodule.DTO.CourseManagementService.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lms.commonmodule.Constants.Enum.SubjectType;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private Integer credits;
    private SubjectType subjectType;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}