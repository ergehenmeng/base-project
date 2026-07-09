package com.eghm.application.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.business.news.NewsQueryRequest;
import com.eghm.application.shared.vo.business.news.NewsResponse;
import com.eghm.application.shared.vo.business.news.NewsVO;

/**
 * 资讯查询端口
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
public interface NewsQueryService {

    /**
     * 分页查询资讯
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<NewsResponse> listPage(Page<NewsResponse> page, NewsQueryRequest request);

    /**
     * 分页查询资讯
     *
     * @param page      分页信息
     * @param queryName 标题查询
     * @return 列表
     */
    Page<NewsVO> getByPage(Page<NewsVO> page, String queryName);
}
