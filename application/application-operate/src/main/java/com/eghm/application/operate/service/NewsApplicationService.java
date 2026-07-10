package com.eghm.application.operate.service;

import cn.hutool.core.collection.CollUtil;
import com.eghm.application.member.service.MemberCollectApplicationService;
import com.eghm.application.shared.cache.CacheService;
import com.eghm.application.shared.common.CommonService;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.application.shared.dto.business.news.NewsAddRequest;
import com.eghm.application.shared.dto.business.news.NewsEditRequest;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.business.news.NewsDetailVO;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.domain.operate.model.News;
import com.eghm.domain.operate.repository.NewsRepository;
import com.eghm.domain.operate.service.NewsDomainService;
import com.eghm.domain.shared.enums.CollectType;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资讯信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Slf4j
@Service
@AllArgsConstructor
public class NewsApplicationService {

    private final NewsRepository newsRepository;

    private final CacheService cacheService;

    private final CommonService commonService;

    private final MemberCollectApplicationService memberCollectService;

    private static final NewsDomainService NEWS_DOMAIN_SERVICE = new NewsDomainService();

    /**
     * 新增资讯
     *
     * @param request 资讯信息
     */
    public void create(NewsAddRequest request) {
        NEWS_DOMAIN_SERVICE.assertTitleAvailable(newsRepository, request.getTitle(), request.getCode(), null);
        News news = DataUtil.copy(request, News.class);
        this.setRequest(news, request.getImageList(), request.getTagList());
        newsRepository.save(news);
    }

    /**
     * 更新资讯
     *
     * @param request 资讯信息
     */
    public void update(NewsEditRequest request) {
        NEWS_DOMAIN_SERVICE.assertTitleAvailable(newsRepository, request.getTitle(), request.getCode(), request.getId());
        News news = DataUtil.copy(request, News.class);
        this.setRequest(news, request.getImageList(), request.getTagList());
        newsRepository.update(news);
    }

    /**
     * 删除资讯
     *
     * @param id 资讯id
     */
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    /**
     * 根据id查询资讯
     *
     * @param id id
     * @return 资讯信息
     */
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

    /**
     * 根据id查询资讯
     *
     * @param id id
     * @return 资讯信息
     */
    public News selectById(Long id) {
        return newsRepository.findById(id);
    }

    /**
     * 更新状态
     *
     * @param id    主键
     * @param state 是否显示
     */
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

    /**
     * 点赞或取消点赞
     *
     * @param id id
     */
    public void praise(Long id) {
        Long memberId = ApiHolder.getMemberId();
        String key = CacheConstant.NEWS_PRAISE + id;
        commonService.praise(key, memberId.toString(), praise -> newsRepository.updatePraiseNum(id, News.praiseDelta(Boolean.TRUE.equals(praise))));
    }

    /**
     * 排序
     *
     * @param id     id
     * @param sortBy 排序 最大999
     */
    public void sortBy(Long id, Integer sortBy) {
        newsRepository.updateSort(id, sortBy);
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
