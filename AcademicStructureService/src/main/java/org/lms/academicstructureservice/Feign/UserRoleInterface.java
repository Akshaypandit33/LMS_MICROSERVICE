package org.lms.academicstructureservice.Feign;

import org.lms.commonmodule.DTO.UserService.UserRoleDTO;
import org.lms.commonmodule.FeignException.FeignServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "USER-SERVICE", configuration = FeignServiceException.class, contextId = "userRole-service-academic-service", path = "/internal")
public interface UserRoleInterface {
    @PostMapping("/addUserRole")
    public ResponseEntity<UserRoleDTO> createUserRole(@RequestParam UUID userId, @RequestParam String roleName);

    @DeleteMapping("/")
    public ResponseEntity<String> removeUserRole(@RequestParam UUID userId, @RequestParam String roleName);

    @GetMapping("/viewAl/{userId}")
    public ResponseEntity<List<UserRoleDTO>> viewAllUserRolesByUserId(@PathVariable("userId") UUID userId);


    @GetMapping("/check/{userId}")
    public ResponseEntity<Boolean> checkUserHaveRolesOrNot(@PathVariable("userId") String userId);

}
