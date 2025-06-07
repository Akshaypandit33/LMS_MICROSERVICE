package org.lms.apigateway.filter;

import java.util.List;
import java.util.UUID;


public class UserInfo {
    private UUID userId;
    private UUID collegeId;
    private List<String> role;
    // getters and setters

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(UUID collegeId) {
        this.collegeId = collegeId;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}

