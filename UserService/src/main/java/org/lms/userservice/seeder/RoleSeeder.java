package org.lms.userservice.seeder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.lms.userservice.Model.Role;
import org.lms.userservice.Repository.RoleRepository;
import org.lms.userservice.seeder.Enum.RoleName;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Order(1)
@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        List<String> defaultRoles = Arrays.asList("ADMIN", "HOD", "FACULTY", "STUDENT", "IT_SUPPORT", "SUPER_ADMIN");

        for (RoleName roleName : RoleName.values()) {
            // Check if the role already exists in the database
            if (roleRepository.findByName(roleName.name()).isEmpty()) {
                Role role = new Role();
                role.setName(roleName.name());
                role.setDescription("Default description for " + roleName.name());
                roleRepository.save(role);
            }
        }
    }
}
