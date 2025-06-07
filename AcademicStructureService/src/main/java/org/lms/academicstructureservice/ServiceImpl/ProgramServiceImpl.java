package org.lms.academicstructureservice.ServiceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.lms.academicstructureservice.DTOMapper.ProgramResponseDTOMapper;
import org.lms.academicstructureservice.Feign.CollegeInterface;

import org.lms.academicstructureservice.Model.Department;
import org.lms.academicstructureservice.Model.Program;
import org.lms.academicstructureservice.Repository.DepartmentRepository;
import org.lms.academicstructureservice.Repository.ProgramRepository;
import org.lms.academicstructureservice.Service.ProgramService;
import org.lms.commonmodule.DTO.AcademicStructureService.Request.ProgramRequestDTO;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.ProgramResponseDTO;
import org.lms.commonmodule.DTO.CollegeService.CollegeDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions.ProgramAlreadyExists;
import org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions.ProgramNotFoundException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final CollegeInterface collegeInterface;
    private final DepartmentRepository departmentRepository;
    @Transactional
    @Override
    public ProgramResponseDTO createProgram(ProgramRequestDTO requestDTO, String collegeId) {
        if(collegeId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"College id can not be null");
        }
        Boolean exists = programRepository
                .existsByCollegeIdAndProgramCode(UUID.fromString(collegeId), requestDTO.getProgramCode());

        if(exists) {
            throw new ProgramAlreadyExists("Program Already exist");
        }
        Program program = Program.builder()
                .name(requestDTO.getName())
                .programCode(requestDTO.getProgramCode())
                .collegeId(UUID.fromString(collegeId))
                .durationYears(requestDTO.getDurationYears())
                .build();
        Program savedProgram = programRepository.save(program);
        CollegeDTO collegeDTO = collegeInterface.getCollegeById(collegeId).getBody();
        return ProgramResponseDTOMapper.convertToDTO(savedProgram,collegeDTO);
    }

    @Override
    public ProgramResponseDTO getProgramById(UUID id, String collegeId) {
        Program program = programRepository.findById(id).orElseThrow(
                () -> new ProgramNotFoundException("Program not found")
        );
        checkCollegeBelongsToThatCollegeOrNot(program,collegeId);
        CollegeDTO collegeDTO = collegeInterface.getCollegeById(collegeId).getBody();

        return ProgramResponseDTOMapper.convertToDTO(program,collegeDTO);
    }

    @Override
    public List<ProgramResponseDTO> getAllPrograms(String collegeId) {
        CollegeDTO collegeDTO = collegeInterface.getCollegeById(collegeId).getBody();
        List<Program> programList = programRepository.findAllByCollegeId(UUID.fromString(collegeId));
        List<ProgramResponseDTO> programResponseDTOList = new ArrayList<>();

        for(Program p : programList) {
            programResponseDTOList.add(ProgramResponseDTOMapper.convertToDTO(p,collegeDTO));
        }
        return programResponseDTOList;
    }

    @Transactional
    @Override
    public ProgramResponseDTO updateProgram(UUID programId, ProgramRequestDTO requestDTO, String collegeId) {


        Program program = programRepository.findById(programId).orElseThrow(
                () -> new ProgramNotFoundException("Program not found")
        );
        checkCollegeBelongsToThatCollegeOrNot(program,collegeId);

        // Check if the new program code already exists for this college (if it's being changed)
        if(!program.getProgramCode().equals(requestDTO.getProgramCode())) {
            Boolean codeExists = programRepository
                    .existsByCollegeIdAndProgramCode(UUID.fromString(collegeId), requestDTO.getProgramCode());
            if(codeExists) {
                throw new ProgramAlreadyExists("Program with this code already exists");
            }
        }

        // Update the program fields
       if(requestDTO.getName() != null) program.setName(requestDTO.getName());
       if(requestDTO.getProgramCode() != null) program.setProgramCode(requestDTO.getProgramCode());
       if(requestDTO.getDurationYears() != null)  program.setDurationYears(requestDTO.getDurationYears());

       Program updatedProgram = programRepository.save(program);
       CollegeDTO collegeDTO = collegeInterface.getCollegeById(collegeId).getBody();

        return ProgramResponseDTOMapper.convertToDTO(updatedProgram, collegeDTO);
    }

    @Transactional
    @Override
    public void deleteProgram(UUID programId, String collegeId) {
        Program program = programRepository.findById(programId).orElseThrow(
                () -> new ProgramNotFoundException("Program not found")
        );
        List<Department> relatedDepartment =
                departmentRepository.findDepartmentsByProgram_IdAndProgram_CollegeId(programId,UUID.fromString(collegeId));
        if(!relatedDepartment.isEmpty())
        {
            throw new BusinessException("Cannot delete program with active departments");
        }
        checkCollegeBelongsToThatCollegeOrNot(program,collegeId);
        programRepository.delete(program);
    }

    @Override
    public Program getProgramByCode(String collegeId, String programCode) {
        Optional<Program> program = programRepository.
                findProgramByCollegeIdAndProgramCode(UUID.fromString(collegeId),programCode.toUpperCase());

        if(program.isEmpty())
        {
            throw new ProgramNotFoundException("Program not found");
        }
        return program.get();
    }


    public void checkCollegeBelongsToThatCollegeOrNot(Program program ,String collegeId)
    {
        if(collegeId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"College id can not be null");
        }

        // Check if the program belongs to the specified college
        if(!program.getCollegeId().equals(UUID.fromString(collegeId))) {
            throw new ProgramNotFoundException("Program not found");
        }
    }
}