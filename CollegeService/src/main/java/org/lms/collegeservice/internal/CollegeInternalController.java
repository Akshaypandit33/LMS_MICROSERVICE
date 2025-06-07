package org.lms.collegeservice.internal;

import lombok.RequiredArgsConstructor;
import org.lms.collegeservice.Service.CollegeService;
import org.lms.commonmodule.DTO.CollegeService.CollegeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/college")
public class CollegeInternalController {
    private final CollegeService collegeService;

    @PostMapping("/add")
    public ResponseEntity<CollegeDTO> createCollege(CollegeDTO collegeDTO)
    {
        return ResponseEntity.ok(collegeService.createCollege(collegeDTO));
    }

    @GetMapping("/{collegeId}")
    public ResponseEntity<CollegeDTO> getCollegeById(@PathVariable String collegeId) {
        CollegeDTO college = collegeService.getCollegeById(UUID.fromString(collegeId));
        return ResponseEntity.ok(college);
    }
    @GetMapping("/code/{collegeCode}")
    public ResponseEntity<CollegeDTO> getCollegeByCode(@PathVariable String collegeCode) {
        CollegeDTO college = collegeService.getCollegeByCode(collegeCode);
        return ResponseEntity.ok(college);
    }

    @GetMapping("/isExists/{collegeId}")
    public Boolean existByCollegeId(@PathVariable UUID collegeId)
    {
        return collegeService.existByCollegeId(collegeId);
    }
}
