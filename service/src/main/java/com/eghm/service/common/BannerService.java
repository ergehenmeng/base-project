package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.banner.BannerAddRequest;
import com.eghm.dto.banner.BannerEditRequest;
import com.eghm.dto.banner.BannerQueryRequest;
import com.eghm.model.Banner;

/**
 * @author 二哥很猛
 * @since 2018/10/17 9:20
 */
public interface BannerService {

    /**
     * 根据条件分页查询录播图列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<Banner> getByPage(BannerQueryRequest request);

    /**
     * 主键查询
     *
     * @param id id
     * @return banner
     */
    Banner getById(Long id);

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
     * @param id 主键
     * @param sort 1~999
     */
    void sort(Long id, Integer sort);

    /**
     * 更新状态
     *
     * @param id 主键
     * @param state 是否可点击
     */
    void updateState(Long id, Boolean state);
}

