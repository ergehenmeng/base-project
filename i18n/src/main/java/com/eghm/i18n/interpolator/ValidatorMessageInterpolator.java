package com.eghm.i18n.interpolator;

import com.eghm.i18n.context.LanguageContextHolder;
import com.eghm.i18n.provider.I18nMessageProvider;
import jakarta.validation.MessageInterpolator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *国际化验证消息插器
 * @author wyb-eghm
 * @since 2026/5/15
 */
public class ValidatorMessageInterpolator implements MessageInterpolator {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{(\\w+)}");
    
    private final I18nMessageProvider messageProvider;
    
    public ValidatorMessageInterpolator(I18nMessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }
    
    @Override
    public String interpolate(String messageTemplate, Context context) {
        return interpolate(messageTemplate, context, resolveLocale());
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        if (messageTemplate == null || !this.isI18nKey(messageTemplate)) {
            return messageTemplate;
        }
        String messageKey = this.extractMessageKey(messageTemplate);
        String resolvedMessage = messageProvider.getMessage(messageKey, locale);
        return this.replaceAnnotationAttributes(resolvedMessage, context);
    }

    private Locale resolveLocale() {
        Locale locale = LanguageContextHolder.getLocale();
        return locale != null ? locale : Locale.getDefault();
    }

    private boolean isI18nKey(String message) {
        return message.startsWith("{") && message.endsWith("}");
    }

    private String extractMessageKey(String message) {
        return message.substring(1, message.length() - 1);
    }

    private String replaceAnnotationAttributes(String message, Context context) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(message);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String attributeName = matcher.group(1);
            Object attributeValue = context.getConstraintDescriptor().getAttributes().get(attributeName);
            if (attributeValue != null) {
                matcher.appendReplacement(result, Matcher.quoteReplacement(attributeValue.toString()));
            } else {
                matcher.appendReplacement(result, matcher.group(0));
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
