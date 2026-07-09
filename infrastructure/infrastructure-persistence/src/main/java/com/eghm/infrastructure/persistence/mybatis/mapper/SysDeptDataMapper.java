package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysDeptDataPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eghm
 */
public interface SysDeptDataMapper extends BaseMapper<SysDeptDataPO> {

    /**
     * 获取用户所拥有的所有部门(数据权限)
     *
     * @param userId 用户id
     * @return 部门id
     */
    List<String> getDeptList(@Param("userId") Long userId);

}
