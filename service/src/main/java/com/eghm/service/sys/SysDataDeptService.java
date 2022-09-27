package com.eghm.service.sys;

import com.eghm.model.SysDataDept;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/17
 */
public interface SysDataDeptService {

    /**
     * 获取用户所拥有的所有部门(数据权限)
     * @param operatorId 用户id
     * @return 部门id
     */
    List<String> getDeptList(Long operatorId);

    /**
     * 插入用户与部门数据权限关联信息
     * @param dept operatorId + deptId
     */
    void insert(SysDataDept dept);

    /**
     * 删除用户对应的部门的数据权限
     * @param operatorId 用户id
     */
    void deleteByOperatorId(Long operatorId);
}
