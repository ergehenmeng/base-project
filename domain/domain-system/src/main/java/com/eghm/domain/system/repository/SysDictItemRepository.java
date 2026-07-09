package com.eghm.domain.system.repository;

import com.eghm.domain.system.model.SysDictItem;

/**
 * 数据字典项仓储
 *
 * @author 二哥很猛
 */
public interface SysDictItemRepository {

    boolean existsShowValue(String nid, String showValue, Long excludeId);

    boolean existsHiddenValue(String nid, Integer hiddenValue, Long excludeId);

    void save(SysDictItem item);

    void update(SysDictItem item);

    void deleteById(Long id);
}
