package com.eghm.application.operate.port.out;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.domain.operate.model.NewsConfig;
import com.eghm.application.shared.vo.business.news.NewsConfigResponse;

import java.util.List;

/**
 * 资讯配置查询端口
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
public interface NewsConfigQueryGateway {

    /**
     * 分页查询资讯配置
     *
     * @param query 分页查询参数
     * @return 列表
     */
    Page<NewsConfig> getByPage(PagingQuery query);

    /**
     * 查询资讯配置 (所有)
     *
     * @return 列表
     */
    List<NewsConfigResponse> getList();
}
