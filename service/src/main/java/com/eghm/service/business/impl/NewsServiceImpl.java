package com.eghm.service.business.impl;

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
import com.eghm.service.business.CommonService;
import com.eghm.service.business.MemberCollectService;
import com.eghm.service.business.NewsService;
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

    private final CommonService commonService;

    private final MemberCollectService memberCollectService;

    @Override
    public Page<NewsResponse> getByPage(NewsQueryRequest request) {
        return newsMapper.listPage(request.createPage(), request);
    }

    @Override
    public void create(NewsAddRequest request) {
        this.redoTitle(request.getTitle(), request.getCode(), null);
        News copy = DataUtil.copy(request, News.class);
        this.setRequest(copy, request.getImageList(), request.getTagList());
        newsMapper.insert(copy);
    }

    @Override
    public void update(NewsEditRequest request) {
        this.redoTitle(request.getTitle(), request.getCode(), request.getId());
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
        records.forEach(newsVO -> newsVO.setHasPraise(hasPraise(newsVO.getId())));
        return records;
    }

    @Override
    public NewsDetailVO detail(Long id) {
        News news = newsMapper.selectById(id);
        if (news == null || Boolean.FALSE.equals(news.getState())) {
            log.info("资讯文字已删除 [{}]", id);
            throw new BusinessException(ErrorCode.NEWS_NULL);
        }
        NewsDetailVO vo = DataUtil.copy(news, NewsDetailVO.class);
        vo.setHasPraise(hasPraise(news.getId()));
        vo.setCollect(memberCollectService.checkCollect(id, CollectType.NEWS));
        return vo;
    }

    @Override
    public News selectById(Long id) {
        return newsMapper.selectById(id);
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

    @Override
    public void updateState(Long id, Boolean state) {
        LambdaUpdateWrapper<News> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(News::getId, id);
        wrapper.set(News::getState, state);
        newsMapper.update(null, wrapper);
    }

    /**
     * 设置请求参数
     *
     * @param copy 资讯
     * @param imageList 图片
     * @param tagList 标签
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
     * @return true: 点赞了, 未点赞
     */
    private Boolean hasPraise(Long id) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return false;
        }
        return cacheService.getHashValue(CacheConstant.NEWS_PRAISE + id, memberId.toString()) != null;
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
