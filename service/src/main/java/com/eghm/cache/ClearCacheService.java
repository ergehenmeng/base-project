package com.eghm.cache;

/**
 * @author 二哥很猛
 * @since 2019/1/14 13:52
 */
public interface ClearCacheService {

    /**
     * 清除系统参数缓存
     */
    void clearSysConfig();

    /**
     * 清除数据字典缓存
     */
    void clearSysDict();

    /**
     * 重新加载敏感词
     */
    void clearSensitiveWord();

    /**
     * 清除支付配置
     *
     */
    void clearPayConfig();

    /**
     * 清除banner缓存
     */
    void clearBanner();

    /**
     * 清除公告缓存
     */
    void clearNotice();

    /**
     * 清除省市区缓存
     */
    void clearSysArea();

    /**
     * 零售标签缓存清除
     */
    void clearItemTag();

    /**
     * 清除授权配置缓存
     */
    void clearAuthConfig();

    /**
     * 清除freemarker模板
     */
    void clearFreemarkerTemplate();

    /**
     * 清除邮件模板
     */
    void clearEmailTemplate();

    /**
     * 清除站内信模板
     */
    void clearInMailTemplate();
}

