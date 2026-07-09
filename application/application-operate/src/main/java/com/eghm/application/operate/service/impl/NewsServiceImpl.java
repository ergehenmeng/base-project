package com.eghm.application.operate.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.dto.ext.Page;
import com.eghm.cache.CacheService;
import com.eghm.common.CommonService;
import com.eghm.configuration.authentication.ApiHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.news.NewsAddRequest;
import com.eghm.dto.business.news.NewsEditRequest;
import com.eghm.dto.business.news.NewsQueryRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.domain.shared.enums.CollectType;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.operate.model.News;
import com.eghm.domain.operate.repository.NewsRepository;
import com.eghm.application.member.service.MemberCollectService;
import com.eghm.application.operate.service.NewsQueryGateway;
import com.eghm.application.operate.service.NewsService;
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
@AllArgsConstructor
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final NewsQueryGateway newsQueryGateway;

    private final CacheService cacheService;

    private final CommonService commonService;

    private final MemberCollectService memberCollectService;

    @Override
    public Page<NewsResponse> getByPage(NewsQueryRequest request) {
        return newsQueryGateway.listPage(request.createPage(), request);
    }

    @Override
    public void create(NewsAddRequest request) {
        this.assertTitleAvailable(request.getTitle(), request.getCode(), null);
        News news = DataUtil.copy(request, News.class);
        this.setRequest(news, request.getImageList(), request.getTagList());
        newsRepository.save(news);
    }

    @Override
    public void update(NewsEditRequest request) {
        this.assertTitleAvailable(request.getTitle(), request.getCode(), request.getId());
        News news = DataUtil.copy(request, News.class);
        this.setRequest(news, request.getImageList(), request.getTagList());
        newsRepository.update(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public List<NewsVO> getByPage(PagingQuery query) {
        Page<NewsVO> byPage = newsQueryGateway.getByPage(query.createPage(false), query.getQueryName());
        List<NewsVO> records = byPage.getRecords();
        records.forEach(newsVO -> newsVO.setHasPraise(this.hasPraise(newsVO.getId())));
        return records;
    }

    @Override
    public NewsDetailVO detail(Long id) {
        News news = newsRepository.findById(id);
        if (news == null) {
            log.info("资讯文字已删除 [{}]", id);
            throw new BusinessException(ErrorCode.NEWS_NULL);
        }
        NewsDetailVO vo = DataUtil.copy(news, NewsDetailVO.class);
        vo.setHasPraise(this.hasPraise(news.getId()));
        vo.setCollect(memberCollectService.checkCollect(id, CollectType.NEWS));
        return vo;
    }

    @Override
    public News selectById(Long id) {
        return newsRepository.findById(id);
    }

    @Override
    public void updateState(Long id, Boolean state) {
        News news = newsRepository.findById(id);
        if (news == null) {
            throw new BusinessException(ErrorCode.NEWS_NULL);
        }
        if (Boolean.TRUE.equals(state)) {
            news.publish();
        } else {
            news.unpublish();
        }
        newsRepository.update(news);
    }

    @Override
    public void praise(Long id) {
        Long memberId = ApiHolder.getMemberId();
        String key = CacheConstant.NEWS_PRAISE + id;
        commonService.praise(key, memberId.toString(), praise -> newsRepository.updatePraiseNum(id, News.praiseDelta(Boolean.TRUE.equals(praise))));
    }

    @Override
    public void sortBy(Long id, Integer sortBy) {
        newsRepository.updateSort(id, sortBy);
    }

    /**
     * 检查同编码下资讯标题是否重复
     *
     * @param title     标题
     * @param code      编码
     * @param excludeId 排除id
     */
    private void assertTitleAvailable(String title, String code, Long excludeId) {
        if (newsRepository.existsByTitleAndCode(title, code, excludeId)) {
            log.warn("资讯标题重复 [{}] [{}]", title, code);
            throw new BusinessException(ErrorCode.NEWS_TITLE_REDO);
        }
    }

    /**
     * 设置请求参数
     *
     * @param news      资讯
     * @param imageList 图片
     * @param tagList   标签
     */
    private void setRequest(News news, List<String> imageList, List<String> tagList) {
        if (CollUtil.isNotEmpty(imageList)) {
            news.setImage(CollUtil.join(imageList, CommonConstant.COMMA));
        }
        if (CollUtil.isNotEmpty(tagList)) {
            news.setTagName(CollUtil.join(tagList, CommonConstant.COMMA));
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
