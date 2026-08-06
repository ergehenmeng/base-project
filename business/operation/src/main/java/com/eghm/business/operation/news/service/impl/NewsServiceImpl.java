package com.eghm.business.operation.news.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.cache.service.CacheService;
import com.eghm.platform.config.service.CommonService;
import com.eghm.foundation.core.configuration.authentication.ApiHolder;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.business.operation.news.dto.NewsAddRequest;
import com.eghm.business.operation.news.dto.NewsEditRequest;
import com.eghm.business.operation.news.dto.NewsQueryRequest;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.enums.CollectType;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.business.operation.news.mapper.NewsMapper;
import com.eghm.business.operation.news.entity.News;
import com.eghm.business.operation.news.service.NewsService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.foundation.web.utility.ValidationUtil;
import com.eghm.business.operation.news.vo.NewsDetailVO;
import com.eghm.business.operation.news.vo.NewsResponse;
import com.eghm.business.operation.news.vo.NewsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资讯信息表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Slf4j
@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    private final CacheService cacheService;

    private final CommonService commonService;


    @Override
    public Page<NewsResponse> getByPage(NewsQueryRequest request) {
        return newsMapper.listPage(request.createPage(), request);
    }

    @Override
    public void create(NewsAddRequest request) {
        ValidationUtil.redoCheck(newsMapper, News::getTitle, request.getTitle(), wrapper -> wrapper.eq(News::getCode, request.getCode()), null, null, ErrorCode.NEWS_TITLE_REDO, "资讯标题重复 [{}] [{}]");
        News copy = DataUtil.copy(request, News.class);
        this.setRequest(copy, request.getImageList(), request.getTagList());
        newsMapper.insert(copy);
    }

    @Override
    public void update(NewsEditRequest request) {
        ValidationUtil.redoCheck(newsMapper, News::getTitle, request.getTitle(), wrapper -> wrapper.eq(News::getCode, request.getCode()), News::getId, request.getId(), ErrorCode.NEWS_TITLE_REDO, "资讯标题重复 [{}] [{}]");
        News copy = DataUtil.copy(request, News.class);
        this.setRequest(copy, request.getImageList(), request.getTagList());
        newsMapper.updateById(copy);
    }

    @Override
    public void deleteById(Long id) {
        newsMapper.deleteById(id);
    }

    @Override
    public List<NewsVO> getByPage(PagingQuery query) {
        Page<NewsVO> byPage = newsMapper.getByPage(query.createPage(false), query.getQueryName());
        List<NewsVO> records = byPage.getRecords();
        records.forEach(newsVO -> newsVO.setHasPraise(this.hasPraise(newsVO.getId())));
        return records;
    }

    @Override
    public NewsDetailVO detail(Long id) {
        News news = newsMapper.selectById(id);
        if (news == null) {
            log.info("资讯文字已删除 [{}]", id);
            throw new BusinessException(ErrorCode.NEWS_NULL);
        }
        NewsDetailVO vo = DataUtil.copy(news, NewsDetailVO.class);
        vo.setHasPraise(this.hasPraise(news.getId()));
        Long memberId = ApiHolder.tryGetMemberId();
        boolean collected = memberId != null && cacheService.hasHashKey(
                String.format(CacheConstant.MEMBER_COLLECT, CollectType.NEWS.getValue(), id),
                String.valueOf(memberId));
        vo.setCollect(collected);
        return vo;
    }

    @Override
    public News selectById(Long id) {
        return newsMapper.selectById(id);
    }

    @Override
    public List<NewsVO> getList(List<Long> ids) {
        return newsMapper.getList(ids);
    }

    @Override
    public List<Long> getIdsByTitle(String title) {
        LambdaQueryWrapper<News> wrapper = Wrappers.lambdaQuery();
        wrapper.select(News::getId);
        wrapper.like(News::getTitle, title);
        return newsMapper.selectList(wrapper).stream().map(News::getId).toList();
    }

    @Override
    public void updateState(Long id, Boolean state) {
        LambdaUpdateWrapper<News> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(News::getId, id);
        wrapper.set(News::getState, state);
        newsMapper.update(null, wrapper);
    }

    @Override
    public void praise(Long id) {
        Long memberId = ApiHolder.getMemberId();
        String key = CacheConstant.NEWS_PRAISE + id;
        commonService.praise(key, memberId.toString(), praise -> newsMapper.updatePraiseNum(id, Boolean.TRUE.equals(praise) ? 1 : -1));
    }

    @Override
    public void sortBy(Long id, Integer sortBy) {
        LambdaUpdateWrapper<News> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(News::getId, id);
        wrapper.set(News::getSort, sortBy);
        newsMapper.update(null, wrapper);
    }

    /**
     * 设置请求参数
     *
     * @param copy      资讯
     * @param imageList 图片
     * @param tagList   标签
     */
    private void setRequest(News copy, List<String> imageList, List<String> tagList) {
        if (CollUtil.isNotEmpty(imageList)) {
            copy.setImage(CollUtil.join(imageList, CommonConstant.COMMA));
        }
        if (CollUtil.isNotEmpty(tagList)) {
            copy.setTagName(CollUtil.join(tagList, CommonConstant.COMMA));
        }
    }

    /**
     * 判断用户是否已对文章或资讯点赞过
     *
     * @param id 文章id
     * @return true: 点赞了, false: 未点赞
     */
    private Boolean hasPraise(Long id) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return false;
        }
        return cacheService.getHashValue(CacheConstant.NEWS_PRAISE + id, memberId.toString()) != null;
    }

}
