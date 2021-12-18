package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysDataDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDataDeptMapper extends BaseMapper<SysDataDept> {

    /**
     * 获取用户所拥有的所有部门(数据权限)
     * @param operatorId 用户id
     * @return 部门id
     */
    List<String> getDeptList(@Param("operatorId") Long operatorId);

    /**
     * 删除用户对应的部门的数据权限
     * @param operatorId 用户id
     */
    void deleteByOperatorId(@Param("operatorId") Long operatorId);
}