package com.eghm.platform.config.service;

@FunctionalInterface
public interface SensitiveWordReloader {
    
    /**
     * 重新加载敏感词
     * @param sync 同步给其他服务 true:同步 false:不同步
     */
    void reloadLexicon(boolean sync);
}
