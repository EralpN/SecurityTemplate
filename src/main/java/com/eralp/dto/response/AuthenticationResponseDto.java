package com.eralp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class {@link AuthenticationResponseDto} is used to return a token to the client after a successful authentication.
 *
 * @author Eralp Nitelik
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseDto {
    private String token;
}
