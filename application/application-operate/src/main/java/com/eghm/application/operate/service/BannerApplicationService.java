package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.banner.BannerAddRequest;
import com.eghm.application.shared.dto.operate.banner.BannerEditRequest;

/**
 * @author 二哥很猛
 * @since 2018/10/17 9:20
 */
public interface BannerApplicationService {

    /**
     * 新增轮播图信息
     *
     * @param request 前台参数
     */
    void create(BannerAddRequest request);

    /**
     * 编辑保存轮播图信息
     *
     * @param request 前台参数
     */
    void update(BannerEditRequest request);

    /**
     * 删除轮播图信息
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 更新排序
     *
     * @param id   主键
     * @param sort 1~999
     */
    void sort(Long id, Integer sort);

    /**
     * 更新状态
     *
     * @param id    主键
     * @param state 是否可点击
     */
    void updateState(Long id, Boolean state);
}

