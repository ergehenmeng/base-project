package com.eghm.service.operate.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.news.config.NewsConfigAddRequest;
import com.eghm.dto.business.news.config.NewsConfigEditRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.NewsConfigMapper;
import com.eghm.model.NewsConfig;
import com.eghm.service.operate.NewsConfigService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.ValidationUtil;
import com.eghm.vo.business.news.NewsConfigResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.utils.StringUtil.isNotBlank;

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
        ValidationUtil.redoCheck(newsConfigMapper, NewsConfig::getTitle, request.getTitle(), request.getId(), NewsConfig::getId, ErrorCode.NEWS_CONFIG_CODE_REDO, "资讯配置编号重复 [{}] [{}]");
        DataUtil.copy(request, NewsConfig.class, newsConfigMapper::updateById);
    }

    @Override
    public void deleteById(Long id) {
        newsConfigMapper.deleteById(id);
    }

    @Override
    public NewsConfig getByCode(String code) {
        LambdaQueryWrapper<NewsConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(NewsConfig::getCode, code);
        wrapper.last(CommonConstant.LIMIT_ONE);
        NewsConfig config = newsConfigMapper.selectOne(wrapper);
        if (config == null) {
            throw new BusinessException(ErrorCode.NEWS_CONFIG_NOT_EXIST);
        }
        return config;
    }
}
