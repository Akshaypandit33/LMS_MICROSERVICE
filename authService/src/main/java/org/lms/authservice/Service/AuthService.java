package org.lms.authservice.Service;

import org.lms.authservice.DTO.LoginRequestDTO;

import java.util.Optional;

public interface AuthService {
    Optional<String> authenticate(LoginRequestDTO loginRequestDTO);

    Boolean ValidateToken(String token);
}
