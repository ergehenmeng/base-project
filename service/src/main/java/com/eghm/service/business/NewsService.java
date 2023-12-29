package com.eghm.service.business;

import com.eghm.dto.business.news.NewsAddRequest;
import com.eghm.dto.business.news.NewsEditRequest;

/**
 * <p>
 * 资讯信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
public interface NewsService {

    /**
     * 新增资讯
     * @param request 资讯信息
     */
    void create(NewsAddRequest request);

    /**
     * 更新资讯
     * @param request 资讯信息
     */
    void update(NewsEditRequest request);

    /**
     * 删除资讯
     * @param id 资讯id
     */
    void deleteById(Long id);
}
