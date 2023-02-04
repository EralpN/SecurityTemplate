package com.eralp.configuration.security.jwt;

import com.eralp.entities.Auth;
import com.eralp.repositories.AuthRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * This class is a filter that validates the JWT token in the Authorization header of incoming {@link HttpServletRequest}.
 * If the token is valid, it sets the user details as the current authentication in the security context.
 *
 * @author Eralp Nitelik
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AuthRepository authRepository;

    /**
     * This method filters incoming HttpServletRequests and validates the JWT token in the Authorization header.
     * If the token is valid, it sets the user details as the current authentication in the security context.
     *
     * @param request     The {@link HttpServletRequest} to filter.
     * @param response    The {@link  HttpServletResponse} to filter.
     * @param filterChain The {@link FilterChain} to continue the request-response process.
     * @throws ServletException if there is an error in the servlet while processing the request.
     * @throws IOException      if there is an error in the input/output while processing the request.
     * @author Eralp Nitelik
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            // do not continue the chain.
            return;
        }
        jwtToken = authHeader.substring(7);
        username = jwtService.extractUsername(jwtToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Auth> userDetails = this.authRepository.findByEmail(username);
            if (userDetails.isPresent() && jwtService.isTokenValid(jwtToken, userDetails.get())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.get().getId(),
                        userDetails.get().getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
