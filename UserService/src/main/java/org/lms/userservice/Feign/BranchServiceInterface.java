package org.lms.userservice.Feign;

import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.FeignException.FeignServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ACADEMIC-STRUCTURE-SERVICE", configuration = FeignServiceException.class, contextId = "user-service-branch-service", path = "/branches")
public interface BranchServiceInterface {
    @GetMapping("/exists/{branchCode}")
    public ResponseEntity<Boolean> existsByBranchCode(
            @PathVariable String branchCode,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId);
}
