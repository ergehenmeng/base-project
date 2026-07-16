package com.eghm.business.operation.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.business.operation.news.dto.config.NewsConfigAddRequest;
import com.eghm.business.operation.news.dto.config.NewsConfigEditRequest;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.business.operation.news.mapper.NewsConfigMapper;
import com.eghm.business.operation.news.entity.NewsConfig;
import com.eghm.business.operation.news.service.NewsConfigService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.foundation.web.utility.MybatisUtil;
import com.eghm.foundation.web.utility.ValidationUtil;
import com.eghm.business.operation.news.vo.NewsConfigResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.foundation.core.utils.StringUtil.isNotBlank;

/**
 * <p>
 * 资讯配置 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Slf4j
@AllArgsConstructor
@Service("newsConfigService")
public class NewsConfigServiceImpl implements NewsConfigService {

    private final NewsConfigMapper newsConfigMapper;

    @Override
    public Page<NewsConfig> getByPage(PagingQuery query) {
        LambdaQueryWrapper<NewsConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.like(isNotBlank(query.getQueryName()), NewsConfig::getTitle, query.getQueryName());
        wrapper.orderByDesc(NewsConfig::getId);
        return newsConfigMapper.selectPage(query.createPage(), wrapper);
    }

    @Override
    public List<NewsConfigResponse> getList() {
        return newsConfigMapper.getList();
    }

    @Override
    public void create(NewsConfigAddRequest request) {
        ValidationUtil.redoCheck(newsConfigMapper, NewsConfig::getTitle, request.getTitle(), null, null, ErrorCode.NEWS_CONFIG_TITLE_REDO, "资讯配置标题重复 [{}] [{}]");
        ValidationUtil.redoCheck(newsConfigMapper, NewsConfig::getCode, request.getCode(), null, null, ErrorCode.NEWS_CONFIG_CODE_REDO, "资讯配置编号重复 [{}] [{}]");
        DataUtil.copy(request, NewsConfig.class, newsConfigMapper::insert);
    }

    @Override
    public void update(NewsConfigEditRequest request) {
        ValidationUtil.redoCheck(newsConfigMapper, NewsConfig::getTitle, request.getTitle(), NewsConfig::getId, request.getId(),  ErrorCode.NEWS_CONFIG_CODE_REDO, "资讯配置编号重复 [{}] [{}]");
        DataUtil.copy(request, NewsConfig.class, newsConfigMapper::updateById);
    }

    @Override
    public void deleteById(Long id) {
        newsConfigMapper.deleteById(id);
    }

    @Override
    public NewsConfig getByCode(String code) {
        NewsConfig config = MybatisUtil.getOne(newsConfigMapper, NewsConfig::getCode, code);
        if (config == null) {
            throw new BusinessException(ErrorCode.NEWS_CONFIG_NOT_EXIST);
        }
        return config;
    }
}
