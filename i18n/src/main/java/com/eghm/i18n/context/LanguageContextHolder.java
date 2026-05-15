package com.eghm.i18n.context;

import java.util.Locale;

public final class LanguageContextHolder {

    private static final ThreadLocal<Locale> LOCALE_HOLDER = ThreadLocal.withInitial(Locale::getDefault);

    public static void setLocale(Locale locale) {
        LOCALE_HOLDER.set(locale);
    }

    public static Locale getLocale() {
        return LOCALE_HOLDER.get();
    }

    public static void clear() {
        LOCALE_HOLDER.remove();
    }
}
