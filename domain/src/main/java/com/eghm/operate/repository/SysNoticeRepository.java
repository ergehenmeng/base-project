package com.eghm.operate.repository;

import com.eghm.operate.model.SysNotice;

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

    /**
     * 发布公告
     *
     * @param id id主键
     */
    void publish(Long id);

    /**
     * 取消发布
     *
     * @param id 主键
     */
    void cancelPublish(Long id);
}
