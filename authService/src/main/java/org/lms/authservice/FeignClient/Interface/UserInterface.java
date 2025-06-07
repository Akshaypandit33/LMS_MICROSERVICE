package org.lms.authservice.FeignClient.Interface;

import org.lms.authservice.FeignClient.Entity.UserDTODetailed;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE", contextId = "user-service-auth-service", path = "/users")
public interface UserInterface {
    @GetMapping("/email")
    public ResponseEntity<UserDTODetailed> findByEmail(@RequestParam String email);
}
