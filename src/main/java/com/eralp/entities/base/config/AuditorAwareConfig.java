package com.eralp.entities.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * This configuration class enables JPA auditing and sets the auditor aware reference to be used.
 * The auditor aware reference is used to provide the user that created or modified the audited entity.
 *
 * @author Eralp Nitelik
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditorAwareConfig {
    /**
     * Creates and returns a bean of type {@link AuditorAware}.
     *
     * @return an instance of {@link CustomAuditorAware}
     * @author Eralp Nitelik
     */
    @Bean
    public AuditorAware<Long> auditorAware() {
        return new CustomAuditorAware();
    }
}
