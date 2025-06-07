package org.lms.userservice.Controller;

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
@RequestMapping("/userRole/")
public class UserRoleController {

    private final UserRoleService userRoleService;
    @PostMapping("/add")
    public ResponseEntity<UserRoleDTO> createUserRole(@RequestBody UserRoleRequests requests)
    {
        return ResponseEntity.ok(userRoleService.addUserRole(requests.getUserId(), requests.getRoleName()));
    }

    @DeleteMapping("/")
    public ResponseEntity<String> removeUserRole(@RequestBody UserRoleRequests requests)
    {
        userRoleService.removeRole(requests.getUserId(),requests.getRoleName());
        return ResponseEntity.ok("User Role removed successfully");
    }

    @GetMapping("/viewAl/{userId}")
    public ResponseEntity<List<UserRoleDTO>> viewAllUserRolesByUserId(@PathVariable("userId") UUID userId)
    {
        return ResponseEntity.ok(userRoleService.viewAllUserRolesByUserId(userId));
    }

    @GetMapping("/rolesByCollege")
    public ResponseEntity<List<UserRoleDTO>> getUserRolesByCollege(@RequestParam String collegeId)
    {
        return ResponseEntity.ok(userRoleService.getUserRolesByCollege(collegeId));
    }

}
