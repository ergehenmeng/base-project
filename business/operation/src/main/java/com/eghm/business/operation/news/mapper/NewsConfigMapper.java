package com.eghm.business.operation.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.business.operation.news.entity.NewsConfig;
import com.eghm.business.operation.news.vo.NewsConfigResponse;

import java.util.List;

/**
 * <p>
 * 资讯配置 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
public interface NewsConfigMapper extends BaseMapper<NewsConfig> {

    /**
     * 获取资讯配置列表
     *
     * @return 资讯配置列表
     */
    List<NewsConfigResponse> getList();
}
