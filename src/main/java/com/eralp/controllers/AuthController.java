package com.eralp.controllers;

import com.eralp.dto.request.AuthenticationRequest;
import com.eralp.dto.request.RegisterRequest;
import com.eralp.dto.response.AuthenticationResponse;
import com.eralp.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@link AuthController} class is a {@link RestController} that handles authentication related requests.
 *
 * @author Eralp Nitelik
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * This method handles user registration requests.
     *
     * @param request  The {@link RegisterRequest} object containing the user's registration information
     * @return a {@link ResponseEntity} with an {@link AuthenticationResponse} object that contains the token
     * @author Eralp Nitelik
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * This method handles user authentication requests.
     *
     * @param request The {@link AuthenticationRequest} object containing the user's authentication information
     * @return a {@link ResponseEntity} with an {@link AuthenticationResponse} object that contains the token
     * @author Eralp Nitelik
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
