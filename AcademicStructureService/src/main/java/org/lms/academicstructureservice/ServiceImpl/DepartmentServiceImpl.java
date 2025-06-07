package org.lms.academicstructureservice.ServiceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lms.academicstructureservice.DTOMapper.DepartmentResponseDTOMapper;
import org.lms.academicstructureservice.Model.Department;
import org.lms.academicstructureservice.Model.Program;
import org.lms.academicstructureservice.Repository.DepartmentRepository;
import org.lms.academicstructureservice.Service.DepartmentService;
import org.lms.academicstructureservice.Service.ProgramService;
import org.lms.commonmodule.DTO.AcademicStructureService.Request.DepartmentRequestDTO;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.DepartmentResponseDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions.DepartmentAlreadyExists;
import org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions.DepartmentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ProgramService programService;

    @Transactional
    @Override
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO requestDTO, String collegeId) {
        if(requestDTO.getProgramCode() == null)
        {
            throw new RuntimeException("program code can not be null");
        }
        Program program = programService.getProgramByCode(collegeId, requestDTO.getProgramCode());

        if(existsByDepartmentCode(requestDTO.getCode(), collegeId))
        {
            throw new DepartmentAlreadyExists("Department already exists");
        }
        Department department = Department.builder()
                .name(requestDTO.getName())
                .code(requestDTO.getCode())
                .program(program)
                .build();
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentResponseDTOMapper.convertToDTO(savedDepartment);
    }

    @Override
    public DepartmentResponseDTO getDepartmentById(UUID departmentId, String collegeId) {
        Department department= departmentRepository.findByIdAndProgram_CollegeId(departmentId,UUID.fromString(collegeId)).orElseThrow(
                () -> new DepartmentNotFoundException("Department not found")
        );
        return DepartmentResponseDTOMapper.convertToDTO(department);
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartments(String collegeId) {
        List<Department> departmentList = departmentRepository.findAllByProgram_CollegeId(UUID.fromString(collegeId));
        List<DepartmentResponseDTO> departmentResponseDTOList = new ArrayList<>();

        for(Department d : departmentList)
        {
            departmentResponseDTOList.add(DepartmentResponseDTOMapper.convertToDTO(d));
        }
        return departmentResponseDTOList;
    }

    @Override
    public List<DepartmentResponseDTO> getDepartmentsByProgramId(String programCode, String collegeId) {
        List<Department> departmentList = departmentRepository.findDepartmentsByProgram_ProgramCodeAndProgram_CollegeId(
                programCode.toUpperCase(),UUID.fromString(collegeId));
        List<DepartmentResponseDTO> departmentResponseDTOList = new ArrayList<>();

        for(Department d : departmentList)
        {
            departmentResponseDTOList.add(DepartmentResponseDTOMapper.convertToDTO(d));
        }
        return departmentResponseDTOList;
    }

    @Transactional
    @Override
    public DepartmentResponseDTO updateDepartment(UUID departmentId, DepartmentRequestDTO requestDTO, String collegeId) {
        Optional<Department> department = departmentRepository.findByIdAndProgram_CollegeId(departmentId,UUID.fromString(collegeId));
        if(department.isEmpty())
        {
            throw new DepartmentNotFoundException("Department Not found");
        }
        Department department1 = department.get();
        if(requestDTO.getName() != null) department1.setName(requestDTO.getName());
        if(requestDTO.getCode() != null) department1.setCode(requestDTO.getCode());
        if(requestDTO.getProgramCode() != null) {
            Program program = programService.getProgramByCode(collegeId,requestDTO.getProgramCode());
            department1.setProgram(program);
        }
        Department savedDepartment = departmentRepository.save(department1);
        return DepartmentResponseDTOMapper.convertToDTO(savedDepartment);
    }

    @Transactional
    @Override
    public void deleteDepartment(UUID departmentId, String collegeId) {
        Department department = departmentRepository.findByIdAndProgram_CollegeId(departmentId,UUID.fromString(collegeId)).orElseThrow(
                () -> new DepartmentNotFoundException("Department not found")
        );
        departmentRepository.delete(department);
    }

    @Override
    public boolean existsByDepartmentCode(String departmentCode, String collegeId) {
        return departmentRepository.existsDepartmentByCodeAndProgram_CollegeId(departmentCode,UUID.fromString(collegeId));
    }

    @Override
    public List<DepartmentResponseDTO> searchDepartments(String searchTerm, String collegeId) {

        List<Department> departmentList = departmentRepository
                .findByCollegeIdAndNameContainingIgnoreCaseOrDepartmentCodeContainingIgnoreCase
                        (
                                UUID.fromString(collegeId),
                                searchTerm
                        );
        List<DepartmentResponseDTO> departmentResponseDTOList = new ArrayList<>();
        for(Department d : departmentList)
        {
            departmentResponseDTOList.add(DepartmentResponseDTOMapper.convertToDTO(d));
        }
        return departmentResponseDTOList;
    }

    @Override
    public long getDepartmentCount(String collegeId) {
        return departmentRepository.countAllByProgram_CollegeId(UUID.fromString(collegeId));
    }
}
