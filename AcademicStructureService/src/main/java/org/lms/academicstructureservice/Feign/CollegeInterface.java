package org.lms.academicstructureservice.Feign;


import org.lms.commonmodule.DTO.CollegeService.CollegeDTO;
import org.lms.commonmodule.FeignException.FeignServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "COLLEGE-SERVICE", contextId = "academic-service-college-service", path = "/internal/college", configuration = FeignServiceException.class)
public interface CollegeInterface {
    @GetMapping("/isExists/{collegeId}")
    public Boolean existByCollegeId(@PathVariable UUID collegeId);

    @GetMapping("/{collegeId}")
    public ResponseEntity<CollegeDTO> getCollegeById(@PathVariable String collegeId);
}
