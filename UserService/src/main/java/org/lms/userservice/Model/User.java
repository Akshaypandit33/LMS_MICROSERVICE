package org.lms.userservice.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email")

})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    private UUID profileId;

    private Boolean isActive = true;
    private String phoneNumber;

    private String collegeId;
    // Many users belong to one college
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt ;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;

    // Relationships

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    public Set<Role> getRoles() {
        if (userRoles == null) return Collections.emptySet();
        return userRoles.stream()
                .map(UserRole::getRole)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @PrePersist
    @PreUpdate
    public void formatEmail() {
        this.email = email.toLowerCase();
    }

}