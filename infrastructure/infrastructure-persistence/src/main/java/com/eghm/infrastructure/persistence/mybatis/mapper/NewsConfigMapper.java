package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.infrastructure.persistence.mybatis.po.NewsConfigPO;
import com.eghm.application.shared.vo.business.news.NewsConfigResponse;

import java.util.List;

/**
 * <p>
 * 资讯配置 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
public interface NewsConfigMapper extends BaseMapper<NewsConfigPO> {

    /**
     * 获取资讯配置列表
     *
     * @return 资讯配置列表
     */
    List<NewsConfigResponse> getList();
}

