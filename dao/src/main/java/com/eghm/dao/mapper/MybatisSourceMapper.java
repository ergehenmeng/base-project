package com.eghm.dao.mapper;

import com.eghm.dao.model.MybatisRoleMenu;
import com.eghm.dao.model.MybatisSuper;
import com.eghm.dao.model.SysConfig;

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
