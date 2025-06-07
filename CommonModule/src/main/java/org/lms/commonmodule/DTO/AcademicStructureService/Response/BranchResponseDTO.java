package org.lms.commonmodule.DTO.AcademicStructureService.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponseDTO {
    private String id;
    private String code;
    private String name;
    private HODInfo Hod;
    private DepartmentInfo departmentInfo;

    @Data
    @Builder
    public static class DepartmentInfo{
        private String id;
        private String name;
        private String code;
    }

    @Data
    @Builder
    public static class HODInfo{
        String employeeId;
        String userId;
        String name;
        String email;
        String phoneNumber;
    }

}
