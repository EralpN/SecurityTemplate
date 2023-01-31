package com.eralp.controllers.test;

import com.eralp.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link TestController} handles requests related to test operations.
 * These endpoints specially test for user roles.
 *
 * @author Eralp Nitelik
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final ApiResponse apiResponse;

    @GetMapping
    public ResponseEntity<ApiResponse> test() {
        return apiResponse.createOkResponse("Test successful!");
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> testUser() {
        return apiResponse.createOkResponse("User test successful!");
    }

    @GetMapping("/manager")
    public ResponseEntity<ApiResponse> testManager() {
        return apiResponse.createOkResponse("Manager test successful!");
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse> testAdmin() {
        return apiResponse.createOkResponse("Admin test successful!");
    }
}
