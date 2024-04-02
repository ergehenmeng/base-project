package com.eghm.service.common;

import java.util.List;

/**
 * @author wyb
 * @since 2021/12/4 11:02
 */
public interface SensitiveWordService {

    /**
     * 判断字符串中包含的敏感词
     *
     * @param keywords 字符串
     * @return 敏感词信息
     */
    List<String> match(String keywords);

    /**
     * 重新加载敏感词
     */
    void reloadLexicon();

    /**
     * 删除敏感词
     *
     * @param keyword 敏感词
     */
    void delete(String keyword);
}
