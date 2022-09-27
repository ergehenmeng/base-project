package com.eghm.mapper;

import com.eghm.model.MybatisRoleMenu;
import com.eghm.model.MybatisSuper;
import com.eghm.model.SysConfig;

/**
 * @author 殿小二
 * @date 2021/2/22
 */
public interface MybatisSourceMapper {

    /**
     * test
     * @param id
     * @return
     */
    SysConfig getById(Long id);


    MybatisRoleMenu getByRoleId(Long roleId);

    MybatisSuper getDiscriminator(Long roleId);
}
