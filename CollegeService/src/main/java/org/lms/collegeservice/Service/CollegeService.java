package org.lms.collegeservice.Service;



import org.lms.commonmodule.DTO.CollegeService.CollegeDTO;

import java.util.List;
import java.util.UUID;

public interface CollegeService {
    CollegeDTO createCollege(CollegeDTO request);
    CollegeDTO updateCollege(CollegeDTO request, UUID collegeId);
    CollegeDTO getCollegeByCode(String collegeCode);
    List<CollegeDTO> getAllColleges();
    void deleteCollege(UUID id);

    CollegeDTO getCollegeById(UUID collegeId);

    Boolean existByCollegeId(UUID id);
}
