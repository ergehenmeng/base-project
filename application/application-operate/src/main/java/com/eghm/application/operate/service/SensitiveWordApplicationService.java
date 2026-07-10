package com.eghm.application.operate.service;

/**
 * @author wyb
 * @since 2021/12/4 11:02
 */
public interface SensitiveWordApplicationService {

    /**
     * 重新加载敏感词
     * @param sync 同步给其他服务 true:同步 false:不同步
     */
    void reloadLexicon(boolean sync);

    /**
     * 添加敏感词
     *
     * @param keyword 敏感词
     */
    void create(String keyword);

    /**
     * 删除敏感词
     *
     * @param id 敏感词id
     */
    void delete(Long id);
}
