package org.lms.authservice.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import org.lms.authservice.Config.Service.CustomUserDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
@Getter
public class JwtUtil {

    private static Key secretKey;

    @Value("${jwt.issuer}")
    private static String issuer;

    public JwtUtil(@Value("${jwt.secret.key}") String secretKey) {
        byte[] byteKey = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(byteKey);
    }


    public static Optional<String> generateToken(Authentication authentication)
    {
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        String jwt=Jwts.builder()
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 *60 *60 *10)) // 10 hours
                .claim("email",authentication.getName())
                .claim("role",authentication.getAuthorities())
                .claim("collegeId", userDetails.getCollegeId())
                .claim("userId", userDetails.getUserId())
                .signWith(secretKey)
                .compact();

        return Optional.ofNullable(jwt);
    }

   public static void validateToken(String token){
        try{
            Jwts.parser().verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        }catch (SignatureException ex)
        {
            throw new JwtException("Invalid exception");
        }
        catch (JwtException ex)
        {
            throw new JwtException("Invalid JWT");
        }
   }

    public static Claims validateTokenAndGetClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token.replace("Bearer ", ""))
                    .getPayload();
        } catch (ExpiredJwtException ex) {
            throw new SecurityException("Token expired", ex);
        } catch (UnsupportedJwtException ex) {
            throw new SecurityException("Token unsupported", ex);
        } catch (MalformedJwtException ex) {
            throw new SecurityException("Token malformed", ex);
        } catch (SecurityException ex) {
            throw new SecurityException("Invalid signature", ex);
        } catch (IllegalArgumentException ex) {
            throw new SecurityException("Token is null or empty", ex);
        }
    }
}
