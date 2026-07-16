package com.eghm.business.operation.news.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.business.operation.news.dto.NewsAddRequest;
import com.eghm.business.operation.news.dto.NewsEditRequest;
import com.eghm.business.operation.news.dto.NewsQueryRequest;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.business.operation.news.entity.News;
import com.eghm.business.operation.news.vo.NewsDetailVO;
import com.eghm.business.operation.news.vo.NewsResponse;
import com.eghm.business.operation.news.vo.NewsVO;

import java.util.List;

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
     * 分页查询资讯
     *
     * @param request 查询条件
     * @return 分页列表
     */
    Page<NewsResponse> getByPage(NewsQueryRequest request);

    /**
     * 新增资讯
     *
     * @param request 资讯信息
     */
    void create(NewsAddRequest request);

    /**
     * 更新资讯
     *
     * @param request 资讯信息
     */
    void update(NewsEditRequest request);

    /**
     * 删除资讯
     *
     * @param id 资讯id
     */
    void deleteById(Long id);

    /**
     * 移动端查询资讯列表
     *
     * @param query 查询条件
     * @return 列表
     */
    List<NewsVO> getByPage(PagingQuery query);

    /**
     * 根据id查询资讯
     *
     * @param id id
     * @return 资讯信息
     */
    NewsDetailVO detail(Long id);

    /**
     * 根据id查询资讯
     *
     * @param id id
     * @return 资讯信息
     */
    News selectById(Long id);

    /**
     * 根据主键批量查询资讯摘要。
     *
     * @param ids 主键列表
     * @return 资讯摘要
     */
    List<NewsVO> getList(List<Long> ids);

    /**
     * 根据标题模糊查询资讯主键。
     *
     * @param title 标题
     * @return 主键列表
     */
    List<Long> getIdsByTitle(String title);

    /**
     * 更新状态
     *
     * @param id    主键
     * @param state 是否显示
     */
    void updateState(Long id, Boolean state);

    /**
     * 点赞或取消点赞
     *
     * @param id id
     */
    void praise(Long id);

    /**
     * 排序
     *
     * @param id     id
     * @param sortBy 排序 最大999
     */
    void sortBy(Long id, Integer sortBy);
}
