package org.lms.commonmodule.DTO.CollegeService;

import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollegeDTO {

    private UUID id;
    private String collegeCode;
    private String logoUrl;
    private String collegeName;
    private String email;
    private String phoneNo;
    private String address;
    private String adminEmailId;

}
