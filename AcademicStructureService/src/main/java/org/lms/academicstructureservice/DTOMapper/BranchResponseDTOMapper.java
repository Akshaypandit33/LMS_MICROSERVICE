package org.lms.academicstructureservice.DTOMapper;
import org.lms.academicstructureservice.Model.Branch;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.BranchResponseDTO;
import org.lms.commonmodule.DTO.UserService.HODResponseDTO;

public class BranchResponseDTOMapper {
    public static BranchResponseDTO convertToDTO(Branch branch, HODResponseDTO hodDTO)
    {
        BranchResponseDTO.HODInfo hodInfo = null;
        BranchResponseDTO.DepartmentInfo department = null;
        if(hodDTO != null && hodDTO.getUserId() != null)
        {
            hodInfo = BranchResponseDTO.HODInfo.builder()
                    .userId(hodDTO.getUserId())
                    .name(hodDTO.getFirstName() + " "+ hodDTO.getLastName())
                    .email(hodDTO.getEmail())
                    .employeeId(hodDTO.getEmployeeId())
                    .phoneNumber(hodDTO.getPhoneNumber())
                    .build();
        }
        if(branch.getDepartment() != null && branch.getDepartment().getId() != null)
        {
            department = BranchResponseDTO.DepartmentInfo.builder()
                    .id(String.valueOf(branch.getDepartment().getId()))
                    .name(branch.getDepartment().getName())
                    .code(branch.getDepartment().getCode())
                    .build();
        }
        return BranchResponseDTO.builder()
                .id(String.valueOf(branch.getId()))
                .code(branch.getCode())
                .name(branch.getName())
                .Hod(hodInfo)
                .departmentInfo(department)
                .build();
    }
}
