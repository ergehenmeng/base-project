package com.eghm.i18n.provider;

import java.util.Locale;

/**
 * 国际化消息提供器
 * @author wyb-eghm
 * @since 2026/5/15
 */
public interface I18nMessageProvider {
    
    /**
     * 格式化后的消息
     *
     * @param message 消息键
     * @param locale 语言
     * @return 消息内容
     */
    String getMessage(String message, Locale locale);
}
