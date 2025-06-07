package org.lms.collegeservice.Controller;

import lombok.RequiredArgsConstructor;
import org.lms.collegeservice.Service.CollegeService;
import org.lms.commonmodule.DTO.CollegeService.CollegeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/college")
public class CollegeController {

    private final CollegeService collegeService;

    @PostMapping("/add")
    public ResponseEntity<CollegeDTO> createCollege(@RequestBody CollegeDTO request)
    {
        return ResponseEntity.ok(collegeService.createCollege(request));
    }

    @PutMapping("/{collegeId}")
    public ResponseEntity<CollegeDTO> updateCollege(
            @RequestBody CollegeDTO request,
            @PathVariable UUID collegeId) {
        CollegeDTO updated = collegeService.updateCollege(request, collegeId);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{collegeCode}")
    public ResponseEntity<CollegeDTO> getCollegeByCode(@PathVariable String collegeCode) {
        CollegeDTO college = collegeService.getCollegeByCode(collegeCode);
        return ResponseEntity.ok(college);
    }

    @GetMapping
    public ResponseEntity<List<CollegeDTO>> getAllColleges() {
        List<CollegeDTO> colleges = collegeService.getAllColleges();
        return ResponseEntity.ok(colleges);
    }

    @DeleteMapping("/{collegeId}")
    public ResponseEntity<Void> deleteCollege(@PathVariable UUID collegeId) {
        collegeService.deleteCollege(collegeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/isExists/{collegeId}")
    public Boolean existByCollegeId(@PathVariable UUID collegeId)
    {
        return collegeService.existByCollegeId(collegeId);
    }

}
