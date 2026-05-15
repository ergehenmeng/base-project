package com.eghm.i18n.provider;

import java.util.Locale;

/**
 * @author wyb-eghm
 * @since 2026/5/15
 */
public class DefaultI18nMessageProvider implements I18nMessageProvider {
    
    @Override
    public String getMessage(String message, Locale locale) {
        return message;
    }
}
