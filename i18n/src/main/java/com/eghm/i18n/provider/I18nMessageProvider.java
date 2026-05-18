package com.eghm.i18n.provider;

import java.util.Locale;

/**
 * 国际化消息提供器
 * @author wyb-eghm
 * @since 2026/5/15
 */
public interface I18nMessageProvider {
    
    /**
     * 默认数据字典key
     */
    String VALIDATOR = "validator";
    
    /**
     * 错误码字典key
     */
    String ERROR = "error";
    
    /**
     * 通过字典key对消息进行国际化处理
     *
     * @param message 消息键
     * @param locale 语言
     * @param key 字典key
     * @return 消息内容
     */
    String getMessage(String message, Locale locale, String key);
}
