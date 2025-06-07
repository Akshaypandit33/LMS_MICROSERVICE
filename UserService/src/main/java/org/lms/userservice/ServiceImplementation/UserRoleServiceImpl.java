package org.lms.userservice.ServiceImplementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lms.commonmodule.DTO.UserService.UserRoleDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.UserServiceExceptions.UserNotFoundException;


import org.lms.userservice.DTOMapper.UserRoleMapper;
import org.lms.userservice.Model.Role;
import org.lms.userservice.Model.UserRole;
import org.lms.userservice.Repository.RoleRepository;
import org.lms.userservice.Repository.UserRepository;
import org.lms.userservice.Repository.UserRoleRepository;
import org.lms.userservice.Service.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserRoleDTO addUserRole(UUID userId, String roleName) {
        roleName = roleName.toUpperCase();
        Optional<Role> role = roleRepository.findByName(roleName);
        if(!userRepository.existsById(userId))
        {
            throw new UserNotFoundException("User not found with this id :"+userId);
        }
        if(role.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
        }
        if(userRoleRepository.existsByUserIdAndRoleId(userId,role.get().getId()))
        {
           throw new ResponseStatusException(HttpStatus.OK," user have this role assigned already");
        }
        UserRole userRole = UserRole.builder()
                .user(userRepository.findById(userId).get())
                .role(role.get())
                .build();
        UserRole saved = userRoleRepository.save(userRole);
        return UserRoleMapper.convertToDTO(saved);
    }

    @Transactional
    @Override
    public void removeRole(UUID userId, String roleName) {
        roleName = roleName.toUpperCase();

        Role role = roleRepository.findByName(roleName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not  found")
        );
        if(!userRoleRepository.existsByUserIdAndRoleId(userId, role.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "this userRole doesn't exists");
        }
        userRoleRepository.deleteUserRole(userId,role.getId());
    }

    @Override
    public List<UserRoleDTO> viewAllUserRolesByUserId(UUID userId) {
        List<UserRole> userRoleList = userRoleRepository.getUserRolesByUserId(userId);
        return UserRoleMapper.convertListToDTO(userRoleList);
    }

    @Override
    public List<UserRoleDTO> getUserRolesByCollege(String collegeId) {
        return UserRoleMapper.convertListToDTO(userRoleRepository.getUserRolesByCollege(UUID.fromString(collegeId)));
    }

    @Override
    public Boolean existsByUserId(UUID userId) {
        return userRoleRepository.existsByUserId(userId);
    }
}
