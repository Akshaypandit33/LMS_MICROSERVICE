package org.lms.academicstructureservice.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.lms.academicstructureservice.Service.BranchService;
import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.AcademicStructureService.Request.BranchRequestDTO;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.BranchResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/branches")
@CrossOrigin(origins = "*")
public class BranchController {


    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchResponseDTO> createBranch(
            @Valid @RequestBody BranchRequestDTO requestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        BranchResponseDTO response = branchService.createBranch(requestDTO, collegeId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<BranchResponseDTO> getBranchById(
            @PathVariable UUID branchId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        BranchResponseDTO response = branchService.getBranchById(branchId, collegeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BranchResponseDTO>> getAllBranches(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        List<BranchResponseDTO> branches = branchService.getAllBranches(collegeId);
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<BranchResponseDTO>> getBranchesByDepartmentId(
            @PathVariable UUID departmentId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        List<BranchResponseDTO> branches = branchService.getBranchesByDepartmentId(departmentId, collegeId);
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/program/{programId}")
    public ResponseEntity<List<BranchResponseDTO>> getBranchesByProgramId(
            @PathVariable UUID programId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        List<BranchResponseDTO> branches = branchService.getBranchesByProgramId(programId, collegeId);
        return ResponseEntity.ok(branches);
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<BranchResponseDTO> updateBranch(
            @PathVariable UUID branchId,
            @Valid @RequestBody BranchRequestDTO requestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        BranchResponseDTO response = branchService.updateBranch(branchId, requestDTO, collegeId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<Void> deleteBranch(
            @PathVariable UUID branchId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        branchService.deleteBranch(branchId, collegeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{branchCode}")
    public ResponseEntity<Boolean> existsByBranchCode(
            @PathVariable String branchCode,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        boolean exists = branchService.existsByBranchCode(branchCode, collegeId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BranchResponseDTO>> searchBranches(
            @RequestParam String searchTerm,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        List<BranchResponseDTO> branches = branchService.searchBranches(searchTerm, collegeId);
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getBranchCount(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        long count = branchService.getBranchCount(collegeId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/department/{departmentId}")
    public ResponseEntity<Long> getBranchCountByDepartment(
            @PathVariable UUID departmentId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        long count = branchService.getBranchCountByDepartment(departmentId, collegeId);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/by-code")
    public ResponseEntity<BranchResponseDTO> getBranchByCode(@RequestParam("branchCode") String branchCode,
                                                             @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        BranchResponseDTO response = branchService.getBranchByCode(branchCode, collegeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkBranch(@RequestParam("branchId") UUID branchId) {
        return ResponseEntity.ok(branchService.existsBranchByBranchId(branchId));
    }
}
