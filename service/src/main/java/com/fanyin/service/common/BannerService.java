package com.fanyin.service.common;

import com.fanyin.common.enums.Channel;
import com.fanyin.dao.model.business.Banner;
import com.fanyin.model.dto.business.banner.BannerQueryRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/10/17 9:20
 */
public interface BannerService {

    /**
     * 根据客户端类型及模板获取轮播图信息
     * @param source 客户端类型
     * @param type banner所属模块,数据字典的值
     * @return 轮播图列表
     */
    List<Banner> getBanner(Channel source, Byte type);

    /**
     * 根据条件分页查询录播图列表
     * @param request 查询条件
     * @return 列表
     */
    PageInfo<Banner> getByPage(BannerQueryRequest request);
}

