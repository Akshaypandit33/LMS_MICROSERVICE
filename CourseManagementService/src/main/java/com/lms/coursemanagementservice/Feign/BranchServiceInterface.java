package com.lms.coursemanagementservice.Feign;

import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.BranchResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "ACADEMIC-STRUCTURE-SERVICE" , contextId = "course-service-branch-service", path = "/branches")
public interface BranchServiceInterface {

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkBranch(@RequestParam("branchId") UUID branchId);
}
