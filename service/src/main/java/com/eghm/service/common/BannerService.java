package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.enums.Channel;
import com.eghm.model.Banner;
import com.eghm.dto.banner.BannerAddRequest;
import com.eghm.dto.banner.BannerEditRequest;
import com.eghm.dto.banner.BannerQueryRequest;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/10/17 9:20
 */
public interface BannerService {

    /**
     * 根据客户端类型及模板获取轮播图信息
     * @param channel 客户端类型
     * @param classify banner所属模块,数据字典的值
     * @return 轮播图列表
     */
    List<Banner> getBanner(Channel channel, Integer classify);

    /**
     * 主键查询
     * @param id id
     * @return banner
     */
    Banner getById(Long id);

    /**
     * 根据条件分页查询录播图列表
     * @param request 查询条件
     * @return 列表
     */
    Page<Banner> getByPage(BannerQueryRequest request);

    /**
     * 新增轮播图信息
     * @param request 前台参数
     */
    void create(BannerAddRequest request);

    /**
     * 编辑保存轮播图信息
     * @param request 前台参数
     */
    void update(BannerEditRequest request);
}

