package com.eghm.service.business;

import com.eghm.dto.business.news.config.NewsConfigAddRequest;
import com.eghm.dto.business.news.config.NewsConfigEditRequest;

/**
 * <p>
 * 资讯配置 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
public interface NewsConfigService {

    /**
     * 新增资讯配置
     * @param request 资讯配置新增请求
     */
    void create(NewsConfigAddRequest request);

    /**
     * 修改资讯配置
     * @param request 资讯配置修改请求
     */
    void update(NewsConfigEditRequest request);

    /**
     * 删除资讯配置
     * @param id id
     */
    void deleteById(Long id);
}
