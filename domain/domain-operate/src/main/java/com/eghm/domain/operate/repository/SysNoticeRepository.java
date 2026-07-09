package com.eghm.domain.operate.repository;

import com.eghm.domain.operate.model.SysNotice;

/**
 * 系统公告仓储接口
 *
 * @author 二哥很猛
 */
public interface SysNoticeRepository {

    /**
     * 根据id查询公告
     *
     * @param id id
     * @return 公告信息
     */
    SysNotice findById(Long id);

    /**
     * 添加公告
     *
     * @param notice 公告信息
     */
    void save(SysNotice notice);

    /**
     * 更新公告
     *
     * @param notice 公告信息
     */
    void update(SysNotice notice);

    /**
     * 删除公告
     *
     * @param id 公告id
     */
    void deleteById(Long id);

}
