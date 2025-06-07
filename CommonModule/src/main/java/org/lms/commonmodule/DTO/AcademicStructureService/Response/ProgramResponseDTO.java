package org.lms.commonmodule.DTO.AcademicStructureService.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lms.commonmodule.DTO.CollegeService.CollegeDTO;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgramResponseDTO {
    private String id;
    private String name;
    private String programCode;
    private int durationYears;
    private CollegeDTO collegeInfo;


}
