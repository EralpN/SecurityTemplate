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
 * A class representing the request for user authentication.
 * It contains fields which are required for authentication.
 *
 * @author Eralp Nitelik
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @Size(min = 3, message = "Email must be valid.")
    @Email(message = "Email must be valid.")
    @NotBlank(message = "Email must be valid.")
    @NotNull(message = "Email must be valid.")
    private String email;

    @Size(min = 8, max = 256, message = "Password should have at least 8 characters")
    @NotBlank(message = "Password must be valid.")
    @NotNull(message = "Password must be valid.")
    private String password;
}
