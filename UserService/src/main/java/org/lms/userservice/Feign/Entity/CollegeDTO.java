package org.lms.userservice.Feign.Entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CollegeDTO {
    private UUID id;
    private String collegeId;
    private String logoUrl;
    private String collegeName;
    private String email;
    private String phoneNo;
    private String address;
    private UUID adminId;
}
