package com.eralp.configuration.locale;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * LocaleConfig is a class used for configuring the locale.
 * This class extends the {@link AcceptHeaderLocaleResolver} and implements {@link WebMvcConfigurer}.
 *
 * @author Eralp Nitelik
 */
@Configuration
public class LocaleConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    /**
     * This is a list of locales that the application will support other than the default english locale.
     */
    private static final List<Locale> LOCALES = List.of(
            new Locale("tr")
    );

    /**
     * Resolves the locale based on the "Accept-Language" header in the HTTP request.
     * If the header is not present or empty, returns the default locale.
     *
     * @param request the request that carries locale info
     * @return {@link Locale} representing the resolved locale
     */
    @Override
    @NonNull
    public Locale resolveLocale(HttpServletRequest request) {
        String localeHeader = request.getHeader("Accept-Language");
        return localeHeader == null || localeHeader.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(localeHeader), LOCALES);
    }

    /**
     * Defines a bean for a {@link ResourceBundleMessageSource} with basename and default encoding.
     *
     * @return the configured {@link ResourceBundleMessageSource}
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
