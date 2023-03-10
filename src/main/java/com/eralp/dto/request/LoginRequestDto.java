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
public class LoginRequestDto {
    @Size(min = 3, message = "{validation.email.size}")
    @Email(message = "{validation.email.valid}")
    @NotBlank(message = "{validation.email.blank}")
    @NotNull(message = "{validation.email.null}")
    private String email;

    @Size(min = 8, max = 256, message = "{validation.password.size}")
    @NotBlank(message = "{validation.password.blank}")
    @NotNull(message = "{validation.password.null}")
    private String password;
}
