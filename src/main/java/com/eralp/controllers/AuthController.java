package com.eralp.controllers;

import com.eralp.dto.ApiResponse;
import com.eralp.dto.request.LoginRequestDto;
import com.eralp.dto.request.RegisterRequestDto;
import com.eralp.dto.response.LoginResponseDto;
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
    private final ApiResponse apiResponse;
    private final AuthService authService;

    /**
     * This method handles user registration requests.
     *
     * @param request The {@link RegisterRequestDto} object containing the user's registration information
     * @return an {@link ApiResponse} with an {@link LoginResponseDto} object that contains the token inside {@link ResponseEntity}
     * @author Eralp Nitelik
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegisterRequestDto request) {
        return apiResponse.createOkResponse(authService.register(request));
    }

    /**
     * This method handles user authentication requests.
     *
     * @param request The {@link LoginRequestDto} object containing the user's authentication information
     * @return an {@link ApiResponse} with an {@link LoginResponseDto} object that contains the token inside {@link ResponseEntity}
     * @author Eralp Nitelik
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequestDto request) {
        return apiResponse.createOkResponse(authService.login(request));
    }
}
