package org.lms.collegeservice.Constants;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class HeadersConstant {

    // Standard HTTP headers
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";

    // Custom application headers
    public static final String USER_ID = "userId";
    public static final String COLLEGE_ID = "collegeId";
    public static final String EMAIL = "email";
    public static final String ROLES = "roles";
}
