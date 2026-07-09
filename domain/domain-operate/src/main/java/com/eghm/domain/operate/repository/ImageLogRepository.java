package com.eghm.domain.operate.repository;

import com.eghm.domain.operate.model.ImageLog;

/**
 * 图片上传记录仓储接口
 *
 * @author 二哥很猛
 */
public interface ImageLogRepository {

    /**
     * 保存图片记录
     *
     * @param imageLog 图片记录
     */
    void save(ImageLog imageLog);

    /**
     * 更新图片记录
     *
     * @param imageLog 图片记录
     */
    void update(ImageLog imageLog);

    /**
     * 删除图片记录信息
     *
     * @param id id
     */
    void deleteById(Long id);
}
