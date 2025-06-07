package org.lms.authservice.Config.Service;

import lombok.RequiredArgsConstructor;
import org.lms.authservice.FeignClient.Entity.UserDTODetailed;
import org.lms.authservice.FeignClient.Interface.UserInterface;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInterface userInterface;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTODetailed user = userInterface.findByEmail(username).getBody();
        if(user == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found ");
        }

        // Map roles to authorities
        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        UUID collegeId;
        if(user.getCollegeId() == null)
        {
            collegeId = UUID.fromString("nocollegeId");
        }else {
            collegeId = user.getCollegeId(); // assuming getCollege() is not null
        }
        return new CustomUserDetail(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                collegeId
        );

    }
}
