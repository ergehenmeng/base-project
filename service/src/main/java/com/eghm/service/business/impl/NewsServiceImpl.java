package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.news.NewsAddRequest;
import com.eghm.dto.business.news.NewsEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.NewsMapper;
import com.eghm.model.News;
import com.eghm.service.business.NewsService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资讯信息表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Slf4j
@Service("newsService")
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    @Override
    public void create(NewsAddRequest request) {
        this.redoTitle(request.getTitle(), request.getCode(), null);
        News copy = DataUtil.copy(request, News.class);
        newsMapper.insert(copy);
    }

    @Override
    public void update(NewsEditRequest request) {
        this.redoTitle(request.getTitle(), request.getCode(), request.getId());
        News copy = DataUtil.copy(request, News.class);
        newsMapper.updateById(copy);
    }

    @Override
    public void deleteById(Long id) {
        newsMapper.deleteById(id);
    }

    /**
     * 检查标题是否重复
     * @param title 标题
     * @param code 编号
     * @param id id
     */
    private void redoTitle(String title, String code, Long id) {
        LambdaQueryWrapper<News> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(News::getTitle, title);
        wrapper.eq(News::getCode, code);
        wrapper.ne(id != null, News::getId, id);
        Long count = this.newsMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.NEWS_TITLE_REDO);
        }
    }
}
