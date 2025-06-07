package org.lms.commonmodule.DTO.AcademicStructureService.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProgramRequestDTO {

    @NotBlank(message = "Program code is required")
    @Size(min = 2, max = 10, message = "Program code must be between 2 and 10 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Program code must contain only uppercase letters and numbers")
    private String programCode;

    @NotBlank(message = "Program name is required")
    @Size(min = 3, max = 100, message = "Program name must be between 3 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-&().,]+$", message = "Program name contains invalid characters")
    private String name;

    @NotNull(message = "Duration in years is required")
    @Min(value = 1, message = "Duration must be at least 1 year")
    @Max(value = 10, message = "Duration cannot exceed 10 years")
    private Integer durationYears;

    @Valid
    @NotNull(message = "College information is required")
    private CollegeInfoDTO collegeInfo;

    @Data
    @Builder
    public static class CollegeInfoDTO {

        @NotBlank(message = "College code is required")
        @Size(min = 2, max = 20, message = "College code must be between 2 and 20 characters")
        @Pattern(regexp = "^[A-Z0-9]+$", message = "College code must contain only uppercase letters and numbers")
        private String collegeCode;

        @NotBlank(message = "College name is required")
        @Size(min = 3, max = 100, message = "College name must be between 3 and 100 characters")
        @Pattern(regexp = "^[a-zA-Z0-9\\s\\-&().,]+$", message = "College name contains invalid characters")
        private String collegeName;

        @NotBlank(message = "Address is required")
        @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
        private String address;
    }
}