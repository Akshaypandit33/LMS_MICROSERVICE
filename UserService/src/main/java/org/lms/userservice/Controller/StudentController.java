package org.lms.userservice.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lms.commonmodule.Constants.HeadersConstant;
import org.lms.commonmodule.DTO.UserService.Request.StudentRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.StudentResponseDTO;
import org.lms.userservice.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/")
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO studentRequestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return ResponseEntity.ok(studentService.createStudent(studentRequestDTO, collegeId));
    }

    @PutMapping("/{erpId}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable("erpId") String erpId,
            @Valid @RequestBody StudentRequestDTO studentRequestDTO,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return ResponseEntity.ok(studentService.updateStudent(erpId, studentRequestDTO, collegeId));
    }

    @GetMapping("/{erpId}")
    public ResponseEntity<StudentResponseDTO> getStudentByErpId(
            @PathVariable("erpId") String erpId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return ResponseEntity.ok(studentService.getStudentByErpId(erpId, collegeId));
    }

    @DeleteMapping("/{erpId}")
    public ResponseEntity<StudentResponseDTO> deleteStudent(
            @PathVariable("erpId") String erpId,
            @RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return ResponseEntity.ok(studentService.deleteStudent(erpId, collegeId));
    }

    @GetMapping("/")
    public ResponseEntity<StudentResponseDTO> getAllStudents(@RequestHeader(HeadersConstant.COLLEGE_ID) String collegeId) {
        return ResponseEntity.ok(studentService.getAllStudents(collegeId));
    }
}
