package org.lms.academicstructureservice.DTOMapper;

import org.lms.academicstructureservice.Model.Program;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.ProgramResponseDTO;
import org.lms.commonmodule.DTO.CollegeService.CollegeDTO;

public class ProgramResponseDTOMapper {
    public static ProgramResponseDTO convertToDTO(Program program , CollegeDTO collegeDTO)
    {
        return ProgramResponseDTO.builder()
                .id(String.valueOf(program.getId()))
                .name(program.getName())
                .programCode(program.getProgramCode())
                .durationYears(program.getDurationYears())
                .collegeInfo(collegeDTO)
                .build();
    }
}
