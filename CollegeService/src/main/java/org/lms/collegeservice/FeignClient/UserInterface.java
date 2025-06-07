package org.lms.collegeservice.FeignClient;

import jakarta.validation.Valid;
import org.lms.collegeservice.FeignClient.Config.FeignConfig;
import org.lms.collegeservice.FeignClient.Entity.UserDTO;
import org.lms.commonmodule.DTO.UserService.Request.updateRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "USER-SERVICE", contextId = "userController-college-service", path = "/users", configuration = FeignConfig.class)
public interface UserInterface {


    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id,
                                                      @Valid @RequestBody updateRequestDTO request);

    @GetMapping(value = "/email/{email}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email);

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id);
}
