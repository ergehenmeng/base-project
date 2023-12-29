package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.news.config.NewsConfigAddRequest;
import com.eghm.dto.business.news.config.NewsConfigEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.NewsConfigMapper;
import com.eghm.model.NewsConfig;
import com.eghm.service.business.NewsConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public void create(NewsConfigAddRequest request) {

    }

    @Override
    public void update(NewsConfigEditRequest request) {

    }

    @Override
    public void deleteById(Long id) {

    }

    /**
     * 检查标题是否重复
     * @param title 标题
     * @param id id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<NewsConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(NewsConfig::getTitle, title);
        wrapper.ne(id != null, NewsConfig::getId, id);
        Long count = this.newsConfigMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.NEWS_TITLE_REDO);
        }
    }
}
