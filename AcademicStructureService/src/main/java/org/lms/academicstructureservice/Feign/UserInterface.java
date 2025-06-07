package org.lms.academicstructureservice.Feign;

import jakarta.validation.Valid;
import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.UserService.Request.CreateUserDto;
import org.lms.commonmodule.DTO.UserService.Request.updateRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;
import org.lms.commonmodule.FeignException.FeignServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "USER-SERVICE", configuration = FeignServiceException.class, contextId = "user-service-academic-service", path = "/users")
public interface UserInterface {

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDto request);

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() ;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id);

    @GetMapping(value = "/email/{email}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email);

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id,
                                                      @Valid @RequestBody updateRequestDTO request);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id);

    @GetMapping("/checkUser")
    public ResponseEntity<Boolean> checkUserExists(@RequestParam String email,
                                                   @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId);

}
