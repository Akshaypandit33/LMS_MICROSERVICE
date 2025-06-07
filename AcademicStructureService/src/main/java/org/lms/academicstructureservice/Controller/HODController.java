package org.lms.academicstructureservice.Controller;

import lombok.RequiredArgsConstructor;
import org.lms.academicstructureservice.Model.HOD;
import org.lms.academicstructureservice.Service.HODService;
import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.UserService.HODResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hod")
@RequiredArgsConstructor
public class HODController {

    private final HODService hodService;

    @PostMapping("/assign")
    public ResponseEntity<HODResponseDTO> assignHod(
            @RequestParam UUID userId,
            @RequestParam UUID branchId,
            @RequestHeader(HeadersConstant.USER_ID) String assignedBy,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        return ResponseEntity.ok(hodService.assignHod(userId, branchId, UUID.fromString(assignedBy), UUID.fromString(collegeId)));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeHod(
            @RequestParam UUID branchId,
            @RequestParam UUID removedBy,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {

        hodService.removeHod(branchId, removedBy, UUID.fromString(collegeId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/transfer")
    public ResponseEntity<HODResponseDTO> transferHod(
            @RequestParam UUID currentBranchId,
            @RequestParam UUID newBranchId,
            @RequestParam UUID transferredBy,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {


        HODResponseDTO hod = hodService.transferHod(currentBranchId, newBranchId, transferredBy, UUID.fromString(collegeId));
        return ResponseEntity.ok(hod);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<HODResponseDTO> getHodByBranch(@PathVariable UUID branchId,
                                                         @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return hodService.getHodByBranch(branchId,UUID.fromString(collegeId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<HODResponseDTO> getHodByUser(@PathVariable UUID userId,
                                            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId
    ) {
        return hodService.getHodByUser(userId,UUID.fromString(collegeId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/check/user/{userId}")
    public ResponseEntity<Boolean> isUserHod(@PathVariable UUID userId) {
        return ResponseEntity.ok(hodService.isUserHod(userId));
    }

    @GetMapping("/check/branch/{branchId}")
    public ResponseEntity<Boolean> branchHasHod(@PathVariable UUID branchId) {
        return ResponseEntity.ok(hodService.branchHasHod(branchId));
    }
}