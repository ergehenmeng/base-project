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
     * 对消息进行国际化处理
     *
     * @param message 消息键, 可以为错误码, 也可以为普通消息编码, 也可以为数据字典的值
     * @param locale 语言
     * @param args 自定义参数
     * @return 消息内容
     */
    String getMessage(String message, Locale locale, String args);
}
