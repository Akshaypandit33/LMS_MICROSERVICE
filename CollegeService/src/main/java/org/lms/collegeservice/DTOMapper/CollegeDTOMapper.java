package org.lms.collegeservice.DTOMapper;

import org.lms.collegeservice.Model.College;
import org.lms.commonmodule.DTO.CollegeService.CollegeDTO;


import java.util.ArrayList;
import java.util.List;

public class CollegeDTOMapper {

    public static CollegeDTO convertToDTO(College college, String adminEmail)
    {
        return CollegeDTO.builder()
                .id(college.getId())
                .collegeName(college.getCollegeName())
                .collegeCode(college.getCollegeCode())
                .address(college.getAddress())
                .logoUrl(college.getLogoUrl())
                .email(college.getEmail())
                .phoneNo(college.getPhoneNo())
                .adminEmailId(adminEmail)
                .build();
    }

    public static List<CollegeDTO> convertListToDTO(List<College> collegeList)
    {

        List<CollegeDTO> collegeDTOList = new ArrayList<>();
        for(College c: collegeList)
        {

            collegeDTOList.add(convertToDTO(c, ""));
        }
        return collegeDTOList;
    }
}
