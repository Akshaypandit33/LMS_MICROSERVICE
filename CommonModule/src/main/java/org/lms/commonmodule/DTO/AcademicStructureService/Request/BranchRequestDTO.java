package org.lms.commonmodule.DTO.AcademicStructureService.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchRequestDTO {
    private String code;
    private String name;
    private String hod_id;
    private String departmentId;

}
