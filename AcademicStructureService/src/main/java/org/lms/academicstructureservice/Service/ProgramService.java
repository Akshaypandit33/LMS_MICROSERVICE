package org.lms.academicstructureservice.Service;

import org.lms.academicstructureservice.Model.Program;
import org.lms.commonmodule.DTO.AcademicStructureService.Request.ProgramRequestDTO;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.ProgramResponseDTO;
import java.util.List;
import java.util.UUID;

public interface ProgramService {
    ProgramResponseDTO createProgram(ProgramRequestDTO requestDTO, String collegeId);
    ProgramResponseDTO getProgramById(UUID id, String collegeId);
    List<ProgramResponseDTO> getAllPrograms(String collegeId);
    ProgramResponseDTO updateProgram(UUID programId, ProgramRequestDTO requestDTO, String collegeId);
    void deleteProgram(UUID programId, String collegeId);

    Program getProgramByCode(String collegeId, String programCode);
}
