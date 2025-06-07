package org.lms.authservice.ServiceImpl;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.lms.authservice.Config.Service.CustomUserDetailsService;
import org.lms.authservice.DTO.LoginRequestDTO;
import org.lms.authservice.JWT.JwtUtil;
import org.lms.authservice.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SignatureException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImplementation implements AuthService {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        UserDetails userDetail = customUserDetailsService.loadUserByUsername(loginRequestDTO.getEmail());
        if(userDetail == null)
        {
            throw new BadCredentialsException("Invalid Email id");
        }
        // Handle incorrect password scenario
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), userDetail.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetail,
                null,
                userDetail.getAuthorities());
        return JwtUtil.generateToken(authentication);
    }

    @Override
    public Boolean ValidateToken(String token) {
        try{
            JwtUtil.validateToken(token);
            return true;
        }catch (JwtException ex)
        {
            return false;
        }
    }
}
