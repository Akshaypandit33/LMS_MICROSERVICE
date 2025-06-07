package org.lms.academicstructureservice.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.lms.academicstructureservice.Service.ProgramService;
import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.AcademicStructureService.Request.ProgramRequestDTO;
import org.lms.commonmodule.DTO.AcademicStructureService.Response.ProgramResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @PostMapping
    public ResponseEntity<ProgramResponseDTO> createProgram(
            @Valid @RequestBody ProgramRequestDTO requestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        ProgramResponseDTO response = programService.createProgram(requestDTO, collegeId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramResponseDTO> getProgramById(
            @PathVariable UUID programId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        ProgramResponseDTO response = programService.getProgramById(programId, collegeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProgramResponseDTO>> getAllPrograms(
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        List<ProgramResponseDTO> response = programService.getAllPrograms(collegeId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{programId}")
    public ResponseEntity<ProgramResponseDTO> updateProgram(
            @PathVariable UUID programId,
            @Valid @RequestBody ProgramRequestDTO requestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        ProgramResponseDTO response = programService.updateProgram(programId, requestDTO, collegeId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(
            @PathVariable UUID programId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        programService.deleteProgram(programId, collegeId);
        return ResponseEntity.noContent().build();
    }
}