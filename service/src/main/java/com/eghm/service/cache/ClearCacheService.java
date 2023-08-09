package com.eghm.service.cache;

/**
 * @author 二哥很猛
 * @date 2019/1/14 13:52
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
     * 清空短信模板
     */
    void clearSmsTemplate();

    /**
     * 清除推送模板
     */
    void clearPushTemplate();

    /**
     * 零售标签
     */
    void clearItemTag();

    /**
     * 清除freemarker模板
     */
    void clearFreemarkerTemplate();

}

