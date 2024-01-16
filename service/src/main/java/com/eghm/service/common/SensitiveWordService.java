package com.eghm.service.common;

/**
 * @author wyb
 * @date 2021/12/4 11:02
 */
public interface SensitiveWordService {

    /**
     * 判断字符串中是否包含敏感词
     *
     * @param keywords 字符串
     * @return true:包含 false:不包含
     */
    boolean match(String keywords);

    /**
     * 重新加载敏感词
     */
    void reloadLexicon();
}
