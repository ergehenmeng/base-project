package com.eghm.application.operate.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.operate.banner.BannerQueryRequest;
import com.eghm.vo.operate.banner.BannerResponse;

/**
 * 轮播图查询端口
 *
 * @author 二哥很猛
 */
public interface BannerQueryGateway {

    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param request 查询参数
     * @return 列表
     */
    Page<BannerResponse> getByPage(Page<BannerResponse> page, BannerQueryRequest request);
}
