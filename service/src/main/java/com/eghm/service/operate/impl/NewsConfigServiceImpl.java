package com.eghm.service.operate.impl;

import cn.hutool.core.util.StrUtil;
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
import com.eghm.vo.business.news.NewsConfigResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
        wrapper.like(StrUtil.isNotBlank(query.getQueryName()), NewsConfig::getTitle, query.getQueryName());
        wrapper.orderByDesc(NewsConfig::getId);
        return newsConfigMapper.selectPage(query.createPage(), wrapper);
    }

    @Override
    public List<NewsConfigResponse> getList() {
        return newsConfigMapper.getList();
    }

    @Override
    public void create(NewsConfigAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        this.redoCode(request.getCode());
        NewsConfig config = DataUtil.copy(request, NewsConfig.class);
        newsConfigMapper.insert(config);
    }

    @Override
    public void update(NewsConfigEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        NewsConfig config = DataUtil.copy(request, NewsConfig.class);
        newsConfigMapper.updateById(config);
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

    /**
     * 检查标题是否重复
     *
     * @param title 标题
     * @param id    id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<NewsConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(NewsConfig::getTitle, title);
        wrapper.ne(id != null, NewsConfig::getId, id);
        Long count = newsConfigMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.NEWS_CONFIG_TITLE_REDO);
        }
    }

    /**
     * 检查编号是否重复
     *
     * @param code 编号
     */
    private void redoCode(String code) {
        LambdaQueryWrapper<NewsConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(NewsConfig::getCode, code);
        Long count = newsConfigMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.NEWS_CONFIG_CODE_REDO);
        }
    }
}
