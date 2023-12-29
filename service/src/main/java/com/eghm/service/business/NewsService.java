package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.news.NewsAddRequest;
import com.eghm.dto.business.news.NewsEditRequest;
import com.eghm.dto.business.news.NewsQueryRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.model.News;
import com.eghm.vo.business.news.NewsDetailVO;
import com.eghm.vo.business.news.NewsVO;

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
     * @param request 查询条件
     * @return 分页列表
     */
    Page<News> getByPage(NewsQueryRequest request);

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

    /**
     * 移动端查询资讯列表
     * @param query 查询条件
     * @return 列表
     */
    List<NewsVO> getByPage(PagingQuery query);

    /**
     * 根据id查询资讯
     * @param id id
     * @return 资讯信息
     */
    NewsDetailVO detail(Long id);

    /**
     * 点赞或取消点赞
     * @param id id
     */
    void giveLike(Long id);

    /**
     * 排序
     * @param id id
     * @param sortBy 排序 最大999
     */
    void sortBy(Long id, Integer sortBy);
}
