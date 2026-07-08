package com.eghm.sys.repository;

import com.eghm.sys.model.Family;

/**
 * 家谱仓储
 *
 * @author 二哥很猛
 */
public interface FamilyRepository {

    /**
     * 名称是否重复
     *
     * @param name     姓名
     * @param id       当前节点id
     * @param exclude  是否作为排除条件
     * @return true:重复
     */
    boolean existsByNameAndId(String name, String id, boolean exclude);

    /**
     * 查询某节点下的最大子节点id
     *
     * @param pid 父节点id
     * @return 最大子节点id
     */
    String findMaxId(String pid);

    /**
     * 保存家谱节点
     *
     * @param family 家谱节点
     */
    void save(Family family);

    /**
     * 更新家谱节点
     *
     * @param family 家谱节点
     */
    void update(Family family);

    /**
     * 是否存在子节点
     *
     * @param id 节点id
     * @return true:存在
     */
    boolean hasChildren(String id);

    /**
     * 删除家谱节点
     *
     * @param id 节点id
     */
    void deleteById(String id);
}
