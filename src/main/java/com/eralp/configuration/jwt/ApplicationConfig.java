package com.eralp.configuration.jwt;

import com.eralp.entities.Auth;
import com.eralp.repositories.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * This class is a configuration class that contains various beans that are needed by the application.
 * It includes beans for {@link UserDetailsService}, {@link AuthenticationProvider}, {@link AuthenticationManager}, {@link PasswordEncoder}.
 *
 * @author Eralp Nitelik
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final AuthRepository authRepository;

    /**
     * Creates a {@link UserDetailsService} bean that retrieves a {@link Auth} object from the {@link AuthRepository} by email.
     * If the user is not found in the repository, a {@link UsernameNotFoundException} is thrown.
     *
     * @return a {@link UserDetailsService} bean that retrieves user details from the database by email
     * @author Eralp Nitelik
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // In this project email is used as the username on authentication.
            Optional<Auth> user = authRepository.findByEmail(username);
            if (user.isPresent()) {
                return user.get();
            } else {
                throw new UsernameNotFoundException("User not found in database!");
            }
        };
    }

    /**
     * This method creates a Bean of type {@link AuthenticationProvider}
     * It sets the configured {@link UserDetailsService} and {@link PasswordEncoder} to the {@link DaoAuthenticationProvider}.
     *
     * @return {@link AuthenticationProvider} bean.
     * @author Eralp Nitelik
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * This method creates a Bean of type {@link AuthenticationManager}.
     * It returns the {@link AuthenticationManager} from the {@link AuthenticationConfiguration}.
     *
     * @param authenticationConfiguration the {@link AuthenticationConfiguration} to get the {@link AuthenticationManager} from
     * @return {@link AuthenticationManager} bean
     * @throws Exception if there is an error in the configuration.
     * @author Eralp Nitelik
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * This method creates a Bean of type {@link PasswordEncoder} using {@link BCryptPasswordEncoder}.
     *
     * @return a {@link PasswordEncoder} bean for encoding and checking plain text password
     * @author Eralp Nitelik
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
