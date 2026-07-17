package com.eghm.foundation.core.service;

@FunctionalInterface
public interface DictOptionProvider {
    
    /**
     *  根据nid查询某一类数据字典列表
     *
     * @param key 数据字典key
     * @return 数据字典列表数组
     */
    String[] getOptions(String key);
}
