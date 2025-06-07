package org.lms.academicstructureservice.DTOMapper;

import org.lms.academicstructureservice.Model.Department;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.DepartmentResponseDTO;

public class DepartmentResponseDTOMapper {
    public static DepartmentResponseDTO convertToDTO(Department department) {
        if (department == null) return null;

        DepartmentResponseDTO.ProgramInfo program = null;

        if (department.getProgram() != null && department.getProgram().getId() != null) {
            program = DepartmentResponseDTO.ProgramInfo.builder()
                    .id(String.valueOf(department.getProgram().getId()))
                    .programCode(department.getProgram().getProgramCode())
                    .name(department.getProgram().getName())
                    .build();
        }

        return DepartmentResponseDTO.builder()
                .id(String.valueOf(department.getId()))
                .name(department.getName())
                .code(department.getCode())
                .programInfo(program)
                .build();
    }
}
