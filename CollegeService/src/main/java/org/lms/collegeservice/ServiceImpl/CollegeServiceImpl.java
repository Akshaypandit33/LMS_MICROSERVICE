package org.lms.collegeservice.ServiceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lms.collegeservice.DTOMapper.CollegeDTOMapper;
import org.lms.collegeservice.FeignClient.Entity.UserDTO;
import org.lms.collegeservice.FeignClient.Entity.UserRoleDTO;
import org.lms.collegeservice.FeignClient.UserInterface;
import org.lms.collegeservice.FeignClient.UserRoleInterface;
import org.lms.collegeservice.Model.College;
import org.lms.collegeservice.Repository.CollegeRepository;
import org.lms.collegeservice.Service.CollegeService;
import org.lms.commonmodule.DTO.CollegeService.CollegeDTO;
import org.lms.commonmodule.DTO.UserService.Request.updateRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.CollegeServiceException.CollegeAlreadyExists;
import org.lms.commonmodule.ExceptionHandler.Exceptions.CollegeServiceException.CollegeNotFoundException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.UserServiceExceptions.UserRoleAssignmentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CollegeServiceImpl implements CollegeService {
    private final CollegeRepository collegeRepository;
    private final UserRoleInterface userRoleInterface;
    private final UserInterface userInterface;
    @Transactional
    @Override
    public CollegeDTO createCollege(CollegeDTO request) {
        UUID userId = UUID.fromString(Objects.requireNonNull(userInterface.getUserByEmail(request.getAdminEmailId()).getBody()).getUserId());
        Boolean exist = collegeRepository.existsCollegeByCollegeCode(request.getCollegeCode().toUpperCase());
        if(exist)
        {
            throw new CollegeAlreadyExists("College with this code already exists");
        }
        College college = College.builder()
                .collegeName(request.getCollegeName())
                .email(request.getEmail())
                .collegeCode(request.getCollegeCode())
                .address(request.getAddress())
                .logoUrl(request.getLogoUrl())
                .phoneNo(request.getPhoneNo())
                .adminId(userId)
                .build();
        ResponseEntity<UserRoleDTO> userRoleDTO = userRoleInterface.createUserRole(userId,"ADMIN");
        if(userRoleDTO.getStatusCode() != HttpStatus.OK)
        {
            throw new UserRoleAssignmentException("Unable to assign user role");
        }

        College savedCollege = collegeRepository.save(college);


        UserResponseDTO userResponseDTO = userInterface.updateUser(savedCollege.getAdminId(), updateRequestDTO.builder()
                        .collegeId(String.valueOf(savedCollege.getId()))
                .build()).getBody();
        assert userResponseDTO != null;
        return CollegeDTOMapper.convertToDTO(savedCollege, userResponseDTO.getEmail());
    }

    @Override
    public CollegeDTO updateCollege(CollegeDTO updatedCollege, UUID collegeId) {
        College existingCollege =collegeRepository.findById(collegeId).orElseThrow(
                () -> new CollegeNotFoundException("College Not found")
        );

        if (updatedCollege.getCollegeName() != null) {
            existingCollege.setCollegeName(updatedCollege.getCollegeName());
        }
        if (updatedCollege.getLogoUrl() != null)
        {
            existingCollege.setLogoUrl(updatedCollege.getLogoUrl());
        }
        if(updatedCollege.getCollegeCode() != null)
        {
            existingCollege.setCollegeCode(updatedCollege.getCollegeCode());
        }
        if (updatedCollege.getAddress() != null) {
            existingCollege.setAddress(updatedCollege.getAddress());
        }
        if (updatedCollege.getEmail() != null) {
            existingCollege.setEmail(updatedCollege.getEmail());
        }
        if (updatedCollege.getPhoneNo() != null) {
            existingCollege.setPhoneNo(updatedCollege.getPhoneNo());
        }
        return CollegeDTOMapper.convertToDTO(collegeRepository.save(existingCollege),null);
    }

    @Override
    public CollegeDTO getCollegeByCode(String collegeCode) {
        College college =collegeRepository.getCollegeByCollegeCode(collegeCode).orElseThrow(
                () -> new CollegeNotFoundException("College Not found")
        );
        return CollegeDTOMapper.convertToDTO(college, null);
    }

    @Override
    public List<CollegeDTO> getAllColleges() {
        List<College> collegeList = collegeRepository.findAll();

        List<CollegeDTO> collegeDTOList = new ArrayList<>();
        for(College c: collegeList)
        {
            String adminEmailId = Objects.requireNonNull(userInterface.getUserById(c.getAdminId()).getBody()).getEmail();
            collegeDTOList.add(CollegeDTOMapper.convertToDTO(c, adminEmailId));
        }
        return collegeDTOList;
    }

    @Override
    public void deleteCollege(UUID id) {
        collegeRepository.deleteById(id);
    }

    @Override
    public CollegeDTO getCollegeById(UUID collegeId) {
        return CollegeDTOMapper.convertToDTO(collegeRepository.findById(collegeId).orElseThrow(
                () -> new CollegeNotFoundException("College Not found")
        ), null);
    }

    @Override
    public Boolean existByCollegeId(UUID id) {
        return collegeRepository.existsById(id);
    }
}
