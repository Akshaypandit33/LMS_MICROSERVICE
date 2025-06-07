package org.lms.authservice.Controller;

import io.jsonwebtoken.Claims;
import org.lms.authservice.DTO.LoginRequestDTO;
import org.lms.authservice.DTO.LoginResponseDTO;
import org.lms.authservice.FeignClient.Entity.UserDTODetailed;
import org.lms.authservice.FeignClient.Interface.UserInterface;
import org.lms.authservice.JWT.JwtUtil;
import org.lms.authservice.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserInterface userInterface;
    private final AuthService authService;

    public AuthController(UserInterface userInterface, AuthService authService) {
        this.userInterface = userInterface;
        this.authService = authService;
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTODetailed> findUserByEmail(@RequestParam String email)
    {
        return userInterface.findByEmail(email);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        if(authService.authenticate(loginRequestDTO).isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad Request");
        }
        return ResponseEntity.ok(new LoginResponseDTO(authService.authenticate(loginRequestDTO).get()));
    }

//    @GetMapping("/validate")
//    public ResponseEntity<Void> validateToken(
//            @RequestHeader("Authorization") String token
//    )
//    {
//        if(token == null || !token.startsWith("Bearer "))
//        {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        return authService.ValidateToken(token.substring(7)) ? ResponseEntity.ok().build()
//                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            Claims claims = JwtUtil.validateTokenAndGetClaims(token);

            return ResponseEntity.ok(Map.of(
                    "userId", claims.get("userId"),
                    "collegeId", claims.get("collegeId"),
                    "role", claims.get("role", List.class),  // safely cast to List
                    "email", claims.get("email")
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }
}
