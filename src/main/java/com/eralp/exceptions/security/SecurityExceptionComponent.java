package com.eralp.exceptions.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * This class implements {@link AuthenticationEntryPoint} and {@link AccessDeniedHandler}
 * to handle authentication and authorization exceptions respectively.
 * This class uses {@link HandlerExceptionResolver} to resolve exceptions.
 * This allows global exception handler to catch security related exceptions.
 *
 * @author Eralp Nitelik
 */
@Component
@RequiredArgsConstructor
public class SecurityExceptionComponent implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            handlerExceptionResolver.resolveException(request, response, null, authException);
            return;
        }
        handlerExceptionResolver.resolveException(request, response, null, new AccessDeniedException("Must authenticate to access this resource."));
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
    }
}
