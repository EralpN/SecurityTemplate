package com.eralp.controllers.test;

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
public class TestController {
    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test successful!");
    }

    @GetMapping("/user")
    public ResponseEntity<String> testUser() {
        return ResponseEntity.ok("User test successful!");
    }

    @GetMapping("/manager")
    public ResponseEntity<String> testManager() {
        return ResponseEntity.ok("Manager test successful!");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> testAdmin() {
        return ResponseEntity.ok("Admin test successful!");
    }
}
