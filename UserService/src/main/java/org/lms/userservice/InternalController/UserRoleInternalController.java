package org.lms.userservice.InternalController;

import lombok.RequiredArgsConstructor;
import org.lms.commonmodule.DTO.UserService.UserRoleDTO;
import org.lms.userservice.Requests.UserRoleRequests;
import org.lms.userservice.Service.UserRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class UserRoleInternalController {
    private final UserRoleService userRoleService;

    @PostMapping("/addUserRole")
    public ResponseEntity<UserRoleDTO> createUserRole(@RequestParam UUID userId, @RequestParam String roleName)
    {
        return ResponseEntity.ok(userRoleService.addUserRole(userId,roleName));
    }
    @DeleteMapping("/")
    public ResponseEntity<String> removeUserRole(@RequestParam UUID userId, @RequestParam String roleName)
    {
        userRoleService.removeRole(userId,roleName);
        return ResponseEntity.ok("User Role removed successfully");
    }
    @GetMapping("/viewAl/{userId}")
    public ResponseEntity<List<UserRoleDTO>> viewAllUserRolesByUserId(@PathVariable("userId") UUID userId)
    {
        return ResponseEntity.ok(userRoleService.viewAllUserRolesByUserId(userId));
    }

    @GetMapping("/check/{userId}")
    public ResponseEntity<Boolean> checkUserHaveRolesOrNot(@PathVariable("userId") String userId)
    {
        return ResponseEntity.ok(userRoleService.existsByUserId(UUID.fromString(userId)));
    }
}
