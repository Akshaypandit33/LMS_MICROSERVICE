package org.lms.academicstructureservice.ServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lms.academicstructureservice.DTOMapper.BranchResponseDTOMapper;
import org.lms.academicstructureservice.Feign.UserRoleInterface;
import org.lms.academicstructureservice.Model.Branch;
import org.lms.academicstructureservice.Model.Department;
import org.lms.academicstructureservice.Repository.BranchRepository;
import org.lms.academicstructureservice.Repository.DepartmentRepository;
import org.lms.academicstructureservice.Service.BranchService;
import org.lms.academicstructureservice.Service.FacultyProfileService;
import org.lms.commonmodule.DTO.AcademicStructureService.Request.BranchRequestDTO;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.BranchResponseDTO;
import org.lms.commonmodule.DTO.UserService.HODResponseDTO;
import org.lms.commonmodule.DTO.UserService.Response.FacultyResponseDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions.BranchAlreadyExistsException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions.BranchNotFoundException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.AcademicServiceExceptions.DepartmentNotFoundException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRoleInterface userRoleInterface;
    private final FacultyProfileService facultyProfileService;


    @Override
    public BranchResponseDTO createBranch(BranchRequestDTO requestDTO, String collegeId) {
        log.info("Creating branch with code: {} for college: {}", requestDTO.getCode(), collegeId);

        validateBranchRequest(requestDTO, collegeId);

        // Check if branch code already exists
        if (branchRepository.existsByCodeAndDepartment_Program_CollegeId(requestDTO.getCode().toUpperCase(), UUID.fromString(collegeId))) {
            throw new BranchAlreadyExistsException("Branch with code '" + requestDTO.getCode() + "' already exists");
        }

        // Get department
        Department department = getDepartmentById(UUID.fromString(requestDTO.getDepartmentId()), collegeId);

        // Create branch entity
        Branch branch = Branch.builder()
                .code(requestDTO.getCode().toUpperCase())
                .name(requestDTO.getName())
                .department(department)
                .build();

        Branch savedBranch = branchRepository.save(branch);
        log.info("Branch created successfully with ID: {}", savedBranch.getId());

        // Get faculty information if HOD is assigned
//        FacultyDTO facultyDTO = getFacultyInfo(savedBranch.getHod_id(),collegeId);

        return BranchResponseDTOMapper.convertToDTO(savedBranch,null);
    }

    @Override
    @Transactional(readOnly = true)
    public BranchResponseDTO getBranchById(UUID branchId, String collegeId) {
        log.info("Fetching branch with ID: {} for college: {}", branchId, collegeId);

        Branch branch = branchRepository.findByIdAndDepartment_Program_CollegeId(branchId, UUID.fromString(collegeId))
                .orElseThrow(() -> new BranchNotFoundException("Branch not found with ID: " + branchId));

        HODResponseDTO hodResponseDTO = getHODInfo(branch.getHod().getUserId(),collegeId);
        return BranchResponseDTOMapper.convertToDTO(branch, hodResponseDTO);
    }

    @Override
    public BranchResponseDTO getBranchByCode(String branchCode, String collegeId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponseDTO> getAllBranches(String collegeId) {
        log.info("Fetching all branches for college: {}", collegeId);

        List<Branch> branches = branchRepository.findByDepartment_Program_CollegeIdOrderByNameAsc(UUID.fromString(collegeId));

        return branches.stream()
                .map(branch -> {
                    HODResponseDTO hodResponseDTO = getHODInfo(branch.getHod().getUserId(),collegeId);
                    return BranchResponseDTOMapper.convertToDTO(branch, hodResponseDTO);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponseDTO> getBranchesByDepartmentId(UUID departmentId, String collegeId) {
        log.info("Fetching branches for department: {} in college: {}", departmentId, collegeId);

        // Verify department exists
        getDepartmentById(departmentId, collegeId);

        List<Branch> branches = branchRepository.findByDepartmentIdAndDepartment_Program_CollegeId(departmentId, UUID.fromString(collegeId));

        return branches.stream()
                .map(branch -> {
                    HODResponseDTO hodResponseDTO = getHODInfo(branch.getHod().getUserId(),collegeId);
                    return BranchResponseDTOMapper.convertToDTO(branch, hodResponseDTO);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponseDTO> getBranchesByProgramId(UUID programId, String collegeId) {
        log.info("Fetching branches for program: {} in college: {}", programId, collegeId);

        List<Branch> branches = branchRepository.findByDepartment_Program_IdAndDepartment_Program_CollegeId(programId, UUID.fromString(collegeId));

        return branches.stream()
                .map(branch -> {
                    HODResponseDTO hodResponseDTO = getHODInfo(branch.getHod().getUserId(),collegeId);
                    return BranchResponseDTOMapper.convertToDTO(branch, hodResponseDTO);
                })
                .collect(Collectors.toList());
    }

    @Override
    public BranchResponseDTO updateBranch(UUID branchId, BranchRequestDTO requestDTO, String collegeId) {
        log.info("Updating branch with ID: {} for college: {}", branchId, collegeId);

        validateBranchRequest(requestDTO, collegeId);

        Branch existingBranch = branchRepository.findByIdAndDepartment_Program_CollegeId(branchId, UUID.fromString(collegeId))
                .orElseThrow(() -> new BranchNotFoundException("Branch not found with ID: " + branchId));

        // Check if branch code already exists (excluding current branch)
        if (branchRepository.existsByCodeAndDepartment_Program_CollegeIdAndIdNot(
                requestDTO.getCode().toUpperCase(), UUID.fromString(collegeId), branchId)) {
            throw new BranchAlreadyExistsException("Branch with code '" + requestDTO.getCode() + "' already exists");
        }

        // Get department if changed
        Department department = null;
        if (!existingBranch.getDepartment().getId().toString().equals(requestDTO.getDepartmentId())) {
            department = getDepartmentById(UUID.fromString(requestDTO.getDepartmentId()), collegeId);
        } else {
            department = existingBranch.getDepartment();
        }

        // Update branch fields
        if(requestDTO.getCode() != null) existingBranch.setCode(requestDTO.getCode().toUpperCase());
        if(requestDTO.getName() != null) existingBranch.setName(requestDTO.getName());
        if(requestDTO.getDepartmentId() != null) existingBranch.setDepartment(department);


        Branch updatedBranch = branchRepository.save(existingBranch);
        log.info("Branch updated successfully with ID: {}", updatedBranch.getId());

        HODResponseDTO hodResponseDTO = getHODInfo(updatedBranch.getHod().getUserId(),collegeId);

        return BranchResponseDTOMapper.convertToDTO(updatedBranch, hodResponseDTO);
    }

    @Override
    public void deleteBranch(UUID branchId, String collegeId) {
        log.info("Deleting branch with ID: {} for college: {}", branchId, collegeId);

        Branch branch = branchRepository.findByIdAndDepartment_Program_CollegeId(branchId, UUID.fromString(collegeId))
                .orElseThrow(() -> new BranchNotFoundException("Branch not found with ID: " + branchId));

//        // Check if branch has associated programs or courses
//        if (branchRepository.hasProgramsAssociated(branchId)) {
//            throw new ValidationException("Cannot delete branch. It has associated programs.");
//        }
//
//        if (branchRepository.hasCoursesAssociated(branchId)) {
//            throw new ValidationException("Cannot delete branch. It has associated courses.");
//        }

        branchRepository.deleteByIdAndDepartment_Program_CollegeId(branchId, UUID.fromString(collegeId));
        log.info("Branch deleted successfully with ID: {}", branchId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByBranchCode(String branchCode, String collegeId) {
        log.info("Checking existence of branch code: {} for college: {}", branchCode, collegeId);
        return branchRepository
                .existsByCodeAndDepartment_Program_CollegeId(
                        branchCode.toUpperCase(), UUID.fromString(collegeId)
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponseDTO> searchBranches(String searchTerm, String collegeId) {
        log.info("Searching branches with term: '{}' for college: {}", searchTerm, collegeId);

        if (!StringUtils.hasText(searchTerm)) {
            return getAllBranches(collegeId);
        }

        List<Branch> branches = branchRepository.searchBranches(searchTerm.trim(), collegeId);

        return branches.stream()
                .map(branch -> {
                    HODResponseDTO facultyDTO = getHODInfo(branch.getHod().getUserId(),collegeId);
                    return BranchResponseDTOMapper.convertToDTO(branch, facultyDTO);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long getBranchCount(String collegeId) {
        log.info("Getting branch count for college: {}", collegeId);
        return branchRepository.countByDepartment_Program_CollegeId(UUID.fromString(collegeId));
    }

    @Override
    @Transactional(readOnly = true)
    public long getBranchCountByDepartment(UUID departmentId, String collegeId) {
        log.info("Getting branch count for department: {} in college: {}", departmentId, collegeId);

        // Verify department exists
        getDepartmentById(departmentId, collegeId);

        return branchRepository.countByDepartmentIdAndDepartment_Program_CollegeId(departmentId, UUID.fromString(collegeId));
    }

    @Override
    public Boolean existsBranchByBranchId(UUID branchId) {
        return branchRepository.existsById(branchId);
    }

    // Helper methods

    private void validateBranchRequest(BranchRequestDTO requestDTO, String collegeId) {
        if (!StringUtils.hasText(requestDTO.getCode())) {
            throw new ValidationException("Branch code is required");
        }

        if (!StringUtils.hasText(requestDTO.getName())) {
            throw new ValidationException("Branch name is required");
        }

        if (!StringUtils.hasText(requestDTO.getDepartmentId())) {
            throw new ValidationException("Department ID is required");
        }

        // Validate UUID format for departmentId
        try {
            UUID.fromString(requestDTO.getDepartmentId());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid department ID format");
        }

        // Validate UUID format for hod_id if provided
        if (StringUtils.hasText(requestDTO.getHod_id())) {
            try {
                UUID.fromString(requestDTO.getHod_id());
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Invalid HOD ID format");
            }
        }

        // Validate code length and format
        if (requestDTO.getCode().length() > 50) {
            throw new ValidationException("Branch code cannot exceed 50 characters");
        }

        // Validate name length
        if (requestDTO.getName().length() > 100) {
            throw new ValidationException("Branch name cannot exceed 100 characters");
        }
    }

    private Department getDepartmentById(UUID departmentId, String collegeId) {
        return departmentRepository.findByIdAndProgram_CollegeId(departmentId, UUID.fromString(collegeId))
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + departmentId));
    }

    private HODResponseDTO getHODInfo(UUID hodUserId, String collegeId) {
        if (hodUserId == null) {
            return null;
        }

        try {
            FacultyResponseDTO facultyResponseDTO = facultyProfileService
                    .getFacultyByUserId(String.valueOf(hodUserId),collegeId);
            assert facultyResponseDTO != null;
            return HODResponseDTO.builder()
                    .userId(facultyResponseDTO.getUserId())
                    .firstName(facultyResponseDTO.getFirstName())
                    .lastName(facultyResponseDTO.getLastName())
                    .email(facultyResponseDTO.getEmail())
                    .employeeId(facultyResponseDTO.getEmployeeId())
                    .phoneNumber(facultyResponseDTO.getPhoneNumber())
                    .build();
        } catch (Exception e) {
            log.warn("Failed to fetch faculty information for HOD ID: {}", hodUserId, e);
            return null;
        }
    }
}