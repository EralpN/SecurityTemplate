package com.eralp.configuration;

import com.eralp.configuration.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class is used to configure the security settings of the application.
 * It enables web security and provides a bean of type {@link SecurityFilterChain} to configure the HttpSecurity to handle requests.
 *
 * @author Eralp Nitelik
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * This method creates a Bean of type {@link SecurityFilterChain}.
     * It configures the {@link HttpSecurity} to disable CSRF, authorize all requests under given patterns.
     * It also configures the session management to be stateless,
     * sets the authentication provider and
     * adds a custom {@link JwtAuthFilter} filter before {@link UsernamePasswordAuthenticationFilter}.
     *
     * @param httpSecurity The HttpSecurity to configure the filter chain.
     * @return a {@link SecurityFilterChain} bean that configures the {@link HttpSecurity} to handle requests.
     * @throws Exception if there is an error in the configuration.
     * @author Eralp Nitelik
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                // requests below do not need authentication.
                .authorizeHttpRequests()
                .requestMatchers("/auth/**", "/test", "/test/exceptions")
                .permitAll()
                // requests below require certain roles to access content. (Order matters!)
                .requestMatchers("/test/admin", "/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/test/manager", "/manager/**")
                .hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/**")
                .hasAnyRole("ADMIN", "MANAGER", "USER")
                // all other requests require authentication.
                .anyRequest()
                .authenticated()
                .and()
                // authentication should not be stored thus stateless session.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // show spring which authentication provider to use.
                .authenticationProvider(authenticationProvider)
                // to execute our custom filter before UsernamePasswordAuthenticationFilter, this allows us to set securityContext with our filter.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
