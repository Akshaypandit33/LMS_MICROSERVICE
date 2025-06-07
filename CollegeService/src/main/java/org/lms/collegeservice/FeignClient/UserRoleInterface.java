package org.lms.collegeservice.FeignClient;

import org.lms.collegeservice.FeignClient.Config.FeignConfig;
import org.lms.collegeservice.FeignClient.Entity.UserRoleDTO;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Service
@FeignClient(name = "USER-SERVICE",contextId = "userRoleService-college-service", path = "/internal", configuration = FeignConfig.class)
public interface UserRoleInterface {

    @PostMapping("/addUserRole")
    public ResponseEntity<UserRoleDTO> createUserRole(@RequestParam UUID userId, @RequestParam String roleName);

}
