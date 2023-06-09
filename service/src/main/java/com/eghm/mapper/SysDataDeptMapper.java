package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.SysDataDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDataDeptMapper extends BaseMapper<SysDataDept> {

    /**
     * 获取用户所拥有的所有部门(数据权限)
     * @param userId 用户id
     * @return 部门id
     */
    List<String> getDeptList(@Param("userId") Long userId);

}