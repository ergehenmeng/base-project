package com.eghm.mapper;

import com.eghm.model.SysConfig;

/**
 * @author 殿小二
 * @since 2021/2/22
 */
public interface MybatisMapper {

    /**
     * test
     *
     * @param id id
     * @return x
     */
    SysConfig getById(Long id);

    int updateState();

    int updateState2();
}
