package com.eralp.entities.base.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * This class is a custom implementation of the {@link AuditorAware} interface.
 * It provides the current auditor (the user that created or modified the audited entity).
 *
 * @author Eralp Nitelik
 */
public class CustomAuditorAware implements AuditorAware<Long> {
    /**
     * Returns the current auditor. If the auditor is anonymous returns 0 as id.
     *
     * @return an {@link Optional} of the current auditor's id
     * @author Eralp Nitelik
     */
    @Override
    @NonNull
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        if (!"anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.of((Long) authentication.getCredentials());
        }
        return Optional.of(0L);
    }
}
