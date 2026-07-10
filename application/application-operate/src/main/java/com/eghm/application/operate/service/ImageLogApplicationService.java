package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.image.ImageAddRequest;
import com.eghm.application.shared.dto.operate.image.ImageEditRequest;

/**
 * @author 二哥很猛
 * @since 2018/11/27 17:11
 */
public interface ImageLogApplicationService {

    /**
     * 添加图片记录
     *
     * @param request 前台参数
     */
    void create(ImageAddRequest request);

    /**
     * 删除图片记录信息
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 更新图片信息
     *
     * @param request 前台参数
     */
    void update(ImageEditRequest request);

}

