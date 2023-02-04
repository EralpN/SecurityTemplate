package com.eralp.configuration.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * LocaleSelector is a component class that provides a way to get messages based on the locale set in the LocaleContextHolder.
 *
 * @author Eralp Nitelik
 */
@Component
public class LocaleSelector {
    private static ResourceBundleMessageSource messageSource;

    @Autowired
    public LocaleSelector(ResourceBundleMessageSource messageSource) {
        LocaleSelector.messageSource = messageSource;
    }

    /**
     * Returns the message for the given message code based on the locale set in the {@link LocaleContextHolder}.
     *
     * @param msgCode the code representing the message to be retrieved
     * @return the message for the given message code in the current locale
     */
    public static String withCode(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, null, locale);
    }
}
