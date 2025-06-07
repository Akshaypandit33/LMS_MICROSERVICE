package org.lms.commonmodule.DTO.AcademicStructureService.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentResponseDTO {
    private String id;
    private String name;
    private String code;
    private ProgramInfo programInfo;

    @Data
    @Builder
    public static class ProgramInfo {
        private String id;
        private String programCode;
        private String name;
    }


}

