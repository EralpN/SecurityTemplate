package com.eralp.exceptions.security;

import com.eralp.configuration.locale.LocaleSelector;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;


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
            handlerExceptionResolver.resolveException(request, response, null, new InsufficientAuthenticationException(LocaleSelector.withCode("exception.authentication.privilege_insufficient")));
            return;
        }
        handlerExceptionResolver.resolveException(request, response, null, new AccessDeniedException(LocaleSelector.withCode("exception.authentication.not_logged_in")));
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        handlerExceptionResolver.resolveException(request, response, null, new AccessDeniedException(LocaleSelector.withCode("exception.authentication.not_logged_in")));
    }
}
