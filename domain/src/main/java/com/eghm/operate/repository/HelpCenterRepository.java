package com.eghm.operate.repository;

import com.eghm.operate.model.HelpCenter;

/**
 * 帮助中心仓储接口
 *
 * @author 二哥很猛
 */
public interface HelpCenterRepository {

    /**
     * 根据id查询
     *
     * @param id id
     * @return 帮助说明
     */
    HelpCenter findById(Long id);

    /**
     * 保存帮助说明
     *
     * @param helpCenter 帮助说明
     */
    void save(HelpCenter helpCenter);

    /**
     * 更新帮助说明
     *
     * @param helpCenter 帮助说明
     */
    void update(HelpCenter helpCenter);

    /**
     * 排序
     *
     * @param id     id
     * @param sortBy 排序 最大999
     */
    void updateSort(Long id, Integer sortBy);

    /**
     * 删除帮助说明
     *
     * @param id id
     */
    void deleteById(Long id);
}
