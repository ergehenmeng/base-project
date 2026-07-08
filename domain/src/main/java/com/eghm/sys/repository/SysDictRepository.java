package com.eghm.sys.repository;

import com.eghm.sys.model.SysDict;

/**
 * 数据字典仓储
 *
 * @author 二哥很猛
 */
public interface SysDictRepository {

    boolean existsTitle(String title, Long excludeId);

    boolean existsNid(String nid);

    SysDict findById(Long id);

    void save(SysDict dict);

    void update(SysDict dict);
}
