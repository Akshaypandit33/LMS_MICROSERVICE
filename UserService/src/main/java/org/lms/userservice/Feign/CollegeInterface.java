package org.lms.userservice.Feign;

import org.lms.userservice.Feign.Config.FeignConfig;
import org.lms.userservice.Feign.Entity.CollegeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "COLLEGE-SERVICE", path = "/internal",configuration = FeignConfig.class)
public interface CollegeInterface {

    @PostMapping("/add")
    public ResponseEntity<CollegeDTO> createCollege(CollegeDTO collegeDTO);

    @GetMapping("/code/{collegeCode}")
    public ResponseEntity<CollegeDTO> getCollegeByCode(@PathVariable String collegeCode);

}
