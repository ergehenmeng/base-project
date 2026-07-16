package com.eghm.business.operation.news.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.business.operation.news.dto.config.NewsConfigAddRequest;
import com.eghm.business.operation.news.dto.config.NewsConfigEditRequest;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.business.operation.news.entity.NewsConfig;
import com.eghm.business.operation.news.vo.NewsConfigResponse;

import java.util.List;

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

    /**
     * 新增资讯配置
     *
     * @param request 资讯配置新增请求
     */
    void create(NewsConfigAddRequest request);

    /**
     * 修改资讯配置
     *
     * @param request 资讯配置修改请求
     */
    void update(NewsConfigEditRequest request);

    /**
     * 删除资讯配置
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 配置信息
     */
    NewsConfig getByCode(String code);
}
