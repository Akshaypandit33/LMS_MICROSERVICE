package org.lms.userservice.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.UserService.Request.CreateUserDto;
import org.lms.commonmodule.DTO.UserService.Request.updateRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.lms.userservice.Service.UserService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDto request) {
        UserResponseDTO createdUser = userService.createUser(request);

        URI locationURI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(createdUser.getUserId()).toUri();

        return ResponseEntity.created(locationURI).body(createdUser);
    }

    @GetMapping (produces = "application/json")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);

    }

    @GetMapping(value = "/email/{email}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        UserResponseDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);

    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id,
                                                      @Valid @RequestBody updateRequestDTO request) {
        UserResponseDTO updatedUser = userService.updateUser(id, request);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/checkUser")
    public ResponseEntity<Boolean> checkUserExists(@RequestParam String email,
                                                   @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return ResponseEntity.ok(userService.userExists(email, collegeId));
    }
}
