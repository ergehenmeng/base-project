package com.eghm.service.operate.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.news.NewsAddRequest;
import com.eghm.dto.business.news.NewsEditRequest;
import com.eghm.dto.business.news.NewsQueryRequest;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.CollectType;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.NewsMapper;
import com.eghm.model.News;
import com.eghm.service.business.MemberCollectService;
import com.eghm.service.operate.NewsService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.news.NewsDetailVO;
import com.eghm.vo.business.news.NewsResponse;
import com.eghm.vo.business.news.NewsVO;
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
@Service("newsService")
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    private final CacheService cacheService;

    private final MemberCollectService memberCollectService;

    @Override
    public Page<NewsResponse> getByPage(NewsQueryRequest request) {
        return newsMapper.listPage(request.createPage(), request);
    }

    @Override
    public void create(NewsAddRequest request) {
        this.redoTitle(request.getTitle(), request.getCode(), null);
        News copy = DataUtil.copy(request, News.class);
        if (CollUtil.isNotEmpty(request.getImageList())) {
            copy.setImage(CollUtil.join(request.getImageList(), CommonConstant.COMMA));
        }
        newsMapper.insert(copy);
    }

    @Override
    public void update(NewsEditRequest request) {
        this.redoTitle(request.getTitle(), request.getCode(), request.getId());
        News copy = DataUtil.copy(request, News.class);
        if (CollUtil.isNotEmpty(request.getImageList())) {
            copy.setImage(CollUtil.join(request.getImageList(), CommonConstant.COMMA));
        }
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
        records.forEach(newsVO -> newsVO.setIsLiked(this.hasGiveLiked(newsVO.getId())));
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
        vo.setIsLiked(this.hasGiveLiked(news.getId()));
        vo.setCollect(memberCollectService.checkCollect(id, CollectType.NEWS));
        return vo;
    }

    @Override
    public News selectById(Long id) {
        return newsMapper.selectById(id);
    }

    @Override
    public void updateState(Long id, Boolean state) {
        LambdaUpdateWrapper<News> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(News::getId, id);
        wrapper.set(News::getState, state);
        newsMapper.update(null, wrapper);
    }

    @Override
    public void giveLike(Long id) {
        Long memberId = ApiHolder.getMemberId();
        String key = CacheConstant.NEWS_GIVE_LIKE + id;
        boolean isLiked = cacheService.getHashValue(key, memberId.toString()) != null;
        if (isLiked) {
            cacheService.deleteHashKey(key, memberId.toString());
            newsMapper.updateLikeNum(id, -1);
        } else {
            cacheService.setHashValue(key, memberId.toString(), CacheConstant.PLACE_HOLDER);
            newsMapper.updateLikeNum(id, 1);
        }
    }

    @Override
    public void sortBy(Long id, Integer sortBy) {
        LambdaUpdateWrapper<News> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(News::getId, id);
        wrapper.set(News::getSort, sortBy);
        newsMapper.update(null, wrapper);
    }

    /**
     * 判断用户是否已对文章或资讯点赞过
     *
     * @param id 文章id
     * @return true: 点赞了, 未点赞
     */
    private Boolean hasGiveLiked(Long id) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return false;
        }
        return cacheService.getHashValue(CacheConstant.NEWS_GIVE_LIKE + id, memberId.toString()) != null;
    }

    /**
     * 检查标题是否重复
     *
     * @param title 标题
     * @param code  编号
     * @param id    id
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
