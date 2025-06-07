package org.lms.userservice.ServiceImplementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lms.commonmodule.DTO.UserService.Request.CreateUserDto;
import org.lms.commonmodule.DTO.UserService.Request.updateRequestDTO;
import org.lms.commonmodule.DTO.UserService.Response.UserResponseDTO;
import org.lms.commonmodule.ExceptionHandler.Exceptions.UserServiceExceptions.EmailAlreadyExistException;
import org.lms.commonmodule.ExceptionHandler.Exceptions.UserServiceExceptions.UserNotFoundException;
import org.lms.userservice.DTOMapper.UserResponseMapper;
import org.lms.userservice.Model.User;
import org.lms.userservice.Repository.UserRepository;
import org.lms.userservice.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional
    @Override
    public UserResponseDTO createUser(CreateUserDto request) {

        if(userRepository.existsUserByEmail(request.getEmail())) {
            throw new EmailAlreadyExistException("Email Already Exist");
        }
        logger.info("Creating user with email: {}", request.getEmail());
        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .build();
        if(request.getCollegeId() != null && !request.getCollegeId().isEmpty()) {
            user.setCollegeId(request.getCollegeId());
            logger.info("Setting college ID: {}", request.getCollegeId());
        }
        User savedUser = userRepository.save(user);
        logger.info("User created with ID: {}", savedUser.getId());
        return UserResponseMapper.from(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> logger.debug("Fetched user with ID: {}", user.getId()));
        return users.stream()
                .map(UserResponseMapper::from)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        logger.info("Fetching user by ID: {}", id);
        if(id == null) {
            throw new UserNotFoundException("Id can not null");
        }
        User user=  userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );
        logger.debug("User found with ID: {}", id);
        return UserResponseMapper.from(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
         User user = userRepository.findByEmail(email).orElseThrow(
                 () -> new UserNotFoundException("User with email " + email + " not found")
         );
        logger.debug("User found with email: {}", email);
         return UserResponseMapper.from(user);
    }

    @Transactional
    @Override
    public UserResponseDTO updateUser(UUID userId, updateRequestDTO request) {
        logger.info("Updating user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Update user fields
        if(request.getEmail() != null) user.setEmail(request.getEmail());
        if(request.getFirstName() != null)user.setFirstName(request.getFirstName());
        if(request.getLastName() != null)user.setLastName(request.getLastName());
        if(request.getPhoneNumber() != null)user.setPhoneNumber(request.getPhoneNumber());
        if(request.getCollegeId() != null)user.setCollegeId(request.getCollegeId());
        if(request.getProfileId() != null && !request.getProfileId().isEmpty())user.setProfileId(UUID.fromString(request.getProfileId()));
        // Only update password if provided
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Update college ID if provided

        User updatedUser = userRepository.save(user);
        logger.info("User updated with ID: {}", updatedUser.getId());
        return UserResponseMapper.from(updatedUser);
    }

    @Transactional
    @Override
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        logger.info("Deleting user with ID: {}", userId);
        userRepository.delete(user);
        logger.info("User deleted with ID: {}", userId);
    }

    @Override
    public Boolean userExists(String email, String collegeId) {
        return userRepository.existsUserByEmailAndCollegeId(email, collegeId);
    }
}