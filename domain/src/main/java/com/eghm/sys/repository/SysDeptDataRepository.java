package com.eghm.sys.repository;

import com.eghm.sys.model.SysDeptData;

/**
 * 部门数据权限仓储
 *
 * @author eghm
 */
public interface SysDeptDataRepository {

    /**
     * 保存部门数据权限
     *
     * @param deptData 部门数据权限
     */
    void save(SysDeptData deptData);

    /**
     * 删除用户对应的部门数据权限
     *
     * @param userId 用户id
     */
    void deleteByUserId(Long userId);
}
