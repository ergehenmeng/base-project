package com.eghm.foundation.core.service;

@FunctionalInterface
public interface ResourcePathResolver {
    
    /**
     * 解析资源路径 http路径转本地路径
     * @param path 资源路径
     * @return 解析后的资源路径
     */
    String resolve(String path);
}
