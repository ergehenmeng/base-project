package com.eghm.domain.system.repository;

import com.eghm.domain.system.model.BlackRoster;

import java.util.List;

/**
 * 黑名单仓储
 *
 * @author 二哥很猛
 */
public interface BlackRosterRepository {

    /**
     * 新增黑名单
     *
     * @param blackRoster 黑名单
     */
    void save(BlackRoster blackRoster);

    /**
     * 根据id删除
     *
     * @param id 主键
     */
    void deleteById(Long id);

    /**
     * 查询全部黑名单
     *
     * @return 黑名单列表
     */
    List<BlackRoster> findAll();
}
