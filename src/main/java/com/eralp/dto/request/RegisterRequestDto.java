package com.eralp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is used to hold the data needed to register a new user.
 * Authentication data is mandatory.
 *
 * @author Eralp Nitelik
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    @Size(min = 3, message = "Email must have at least 3 characters")
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email must not be blank")
    @NotNull(message = "Email field is required")
    private String email;

    @Size(min = 8, max = 256, message = "Password should be between 8 and 256 characters")
    @NotBlank(message = "Password must not be blank")
    @NotNull(message = "Password field is required")
    private String password;

    // all the fields that you might need when registering...
}
