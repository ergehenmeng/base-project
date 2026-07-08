package com.eghm.sys.repository;

import com.eghm.sys.model.SysDept;

/**
 * 部门仓储
 *
 * @author 二哥很猛
 */
public interface SysDeptRepository {

    /**
     * 部门名称是否重复
     *
     * @param parentCode 父级编号
     * @param title      部门名称
     * @param excludeId  排除id
     * @return true:重复
     */
    boolean existsByParentCodeAndTitle(String parentCode, String title, Long excludeId);

    /**
     * 获取子级最大编号
     *
     * @param code 部门编号
     * @return 最大子级编号
     */
    String findMaxChildCode(String code);

    /**
     * 保存部门
     *
     * @param dept 部门
     */
    void save(SysDept dept);

    /**
     * 更新部门
     *
     * @param dept 部门
     */
    void update(SysDept dept);

    /**
     * 删除部门
     *
     * @param id 主键
     */
    void deleteById(Long id);
}
