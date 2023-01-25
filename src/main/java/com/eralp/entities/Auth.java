package com.eralp.entities;

import com.eralp.entities.base.BaseEntity;
import com.eralp.entities.enums.Role;
import com.eralp.entities.enums.State;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is an entity class that represents a {@link Auth} in the system.
 * It extends {@link BaseEntity} and implements {@link UserDetails} for Spring Security.
 * It contains fields which are required for the authentication of the user.
 * It also provides implementation for the {@link UserDetails} interface for authentication and authorization.
 *
 * @author Eralp Nitelik
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "auths")
public class Auth extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // can implement an account expiration system.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // can implement an account banning system.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // can implement a password expiration system.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // can implement an account activation/deactivation system.
        return getState() == State.ACTIVE;
    }
}
