package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.infrastructure.persistence.mybatis.mapper.NewsConfigMapper;
import com.eghm.domain.operate.model.NewsConfig;
import com.eghm.infrastructure.persistence.mybatis.po.NewsConfigPO;
import com.eghm.application.operate.service.NewsConfigQueryGateway;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.news.NewsConfigResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * 资讯配置 MyBatis 查询适配器
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Repository
@AllArgsConstructor
public class MybatisNewsConfigQueryGateway implements NewsConfigQueryGateway {

    private final NewsConfigMapper newsConfigMapper;

    @Override
    public Page<NewsConfig> getByPage(PagingQuery query) {
        LambdaQueryWrapper<NewsConfigPO> wrapper = Wrappers.lambdaQuery();
        wrapper.like(isNotBlank(query.getQueryName()), NewsConfigPO::getTitle, query.getQueryName());
        wrapper.orderByDesc(NewsConfigPO::getId);
        return MybatisPageUtil.copy(newsConfigMapper.selectPage(MybatisPageUtil.toMybatis(query.createPage()), wrapper), NewsConfig.class);
    }

    @Override
    public List<NewsConfigResponse> getList() {
        return newsConfigMapper.getList();
    }
}

