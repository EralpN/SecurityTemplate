package com.eralp.services;

import com.eralp.configuration.locale.LocaleSelector;
import com.eralp.configuration.security.jwt.JwtService;
import com.eralp.dto.request.AuthenticationRequestDto;
import com.eralp.dto.request.RegisterRequestDto;
import com.eralp.dto.response.AuthenticationResponseDto;
import com.eralp.entities.Auth;
import com.eralp.entities.enums.Role;
import com.eralp.exceptions.custom.UserAlreadyExistsException;
import com.eralp.repositories.AuthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * This class represents the service layer for the {@link Auth} entity.
 *
 * @author Eralp Nitelik
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user.
     *
     * @param request {@link RegisterRequestDto} object containing the information
     * @return {@link AuthenticationResponseDto} object with a generated token for the newly registered user
     * @author Eralp Nitelik
     */
    @Transactional
    public AuthenticationResponseDto register(RegisterRequestDto request) {
        if (authRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(LocaleSelector.withCode("exception.authentication.register.exists"));
        }
        Auth auth = authRepository.save(Auth.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.USER))
                .build());
        log.info("{} registered.", auth.getUsername());
        return AuthenticationResponseDto.builder()
                .token(jwtService.generateToken(auth))
                .build();
    }

    /**
     * Authenticate based on information. (Login)
     *
     * @param request {@link AuthenticationRequestDto} object containing the login information
     * @return {@link AuthenticationResponseDto} object with a generated token
     * @author Eralp Nitelik
     */
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        Optional<Auth> user = authRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(LocaleSelector.withCode("exception.authentication.login.not_exists"));
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        log.info("{} authenticated.", user.get().getUsername());
        return AuthenticationResponseDto.builder()
                .token(jwtService.generateToken(user.get()))
                .build();
    }
}
