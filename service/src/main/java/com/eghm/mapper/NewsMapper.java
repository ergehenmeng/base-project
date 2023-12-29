package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.model.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.news.NewsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资讯信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
public interface NewsMapper extends BaseMapper<News> {

    /**
     * 分页查询资讯
     * @param page 分页信息
     * @param queryName 标题查询
     * @return 列表
     */
    Page<NewsVO> getByPage(Page<NewsVO> page, @Param("queryName") String queryName);

    /**
     * 更新点赞数量
     * @param id id
     * @param giveLike 点赞数量
     */
    void updateGiveLike(@Param("id") Long id, @Param("giveLike") Integer giveLike);
}
