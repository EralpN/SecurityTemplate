package com.eralp.services;

import com.eralp.configuration.locale.LocaleSelector;
import com.eralp.configuration.security.jwt.JwtService;
import com.eralp.dto.request.LoginRequestDto;
import com.eralp.dto.request.RegisterRequestDto;
import com.eralp.dto.response.LoginResponseDto;
import com.eralp.dto.response.RegisterResponseDto;
import com.eralp.entities.Token;
import com.eralp.entities.User;
import com.eralp.entities.enums.Role;
import com.eralp.entities.enums.TokenType;
import com.eralp.exceptions.custom.UserAlreadyExistsException;
import com.eralp.repositories.TokenRepository;
import com.eralp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * This class represents the authentication service layer for the {@link User} entity.
 *
 * @author Eralp Nitelik
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user.
     *
     * @param request {@link RegisterRequestDto} object containing the information
     * @return {@link LoginResponseDto} object with a generated token for the newly registered user
     * @author Eralp Nitelik
     */
    @Transactional
    public RegisterResponseDto register(RegisterRequestDto request) {
        if (userRepository.findActiveUserByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(LocaleSelector.withCode("exception.authentication.register.exists"));
        }
        User user = userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.USER)) // Role is set to user upon registration!
                .build());
        log.info("{} registered.", user.getUsername());
        return RegisterResponseDto.builder()
                .userId(user.getId())
                .build();
    }

    /**
     * Authenticate based on information. (Login)
     *
     * @param request {@link LoginRequestDto} object containing the login information
     * @return {@link LoginResponseDto} object with a generated token
     * @author Eralp Nitelik
     */
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findActiveUserByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(LocaleSelector.withCode("exception.authentication.login.not_exists")));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        revokeAllUserTokens(user);
        String jwtToken = saveAndGetJwtToken(user);
        log.info("{} authenticated.", user.getUsername());
        return LoginResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Generates and saves the generated jwt token for the authenticating user as {@link Token}.
     *
     * @param user {@link User} entity that the token is bound to
     * @return the generated jwt token as String
     * @author Eralp Nitelik
     */
    private String saveAndGetJwtToken(User user) {
        String jwtToken = jwtService.generateToken(user);
        tokenRepository.save(Token.builder()
                .token(jwtToken)
                .user(user)
                .tokenType(TokenType.BEARER)
                .loggedOut(false)
                .revoked(false)
                .build());
        return jwtToken;
    }

    /**
     * Revokes all the tokens that belong to the given {@link User}.
     * This is necessary to make sure a user does not have multiple active tokens at a time.
     *
     * @param user {@link User} entity that own the tokens
     * @author Eralp Nitelik
     */
    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> token.setRevoked(true));
        tokenRepository.saveAll(validUserTokens);
    }
}
