package com.eghm.application.member.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.domain.member.model.MemberCollect;
import com.eghm.domain.member.repository.MemberCollectRepository;
import com.eghm.application.shared.cache.CacheService;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.application.shared.dto.business.collect.CollectQueryDTO;
import com.eghm.application.shared.dto.business.statistics.CollectRequest;
import com.eghm.domain.shared.enums.CollectType;
import com.eghm.domain.shared.enums.SelectType;
import com.eghm.application.member.query.MemberCollectQueryService;
import com.eghm.application.member.service.MemberCollectApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.business.collect.MemberCollectVO;
import com.eghm.application.shared.vo.business.news.NewsVO;
import com.eghm.application.shared.vo.business.statistics.CollectStatisticsVO;
import com.eghm.application.shared.vo.operate.notice.NoticeVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.constants.CacheConstant.MEMBER_COLLECT;
import static com.eghm.enums.CollectType.NEWS;

/**
 * <p>
 * 会员收藏记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
@Slf4j
@AllArgsConstructor
@Service("memberCollectService")
public class MemberCollectApplicationServiceImpl implements MemberCollectApplicationService {

    private final CacheService cacheService;

    private final MemberCollectRepository memberCollectRepository;

    private final MemberCollectQueryService memberCollectQueryGateway;

    @Override
    public List<MemberCollectVO> getByPage(CollectQueryDTO query) {
        Page<MemberCollectVO> byPage = memberCollectQueryGateway.getByPage(query.createPage(false), query);
        if (CollUtil.isNotEmpty(byPage.getRecords())) {
            Map<CollectType, List<Long>> collectMap = byPage.getRecords().stream().collect(Collectors.groupingBy(MemberCollectVO::getCollectType, Collectors.mapping(MemberCollectVO::getCollectId, Collectors.toList())));
            Map<Long, NewsVO> newsMap = this.getNewsMap(collectMap.get(NEWS));
            Map<Long, NoticeVO> noticeMap = this.getNoticeMap(collectMap.get(CollectType.NOTICE));
            Iterator<MemberCollectVO> iterator = byPage.getRecords().iterator();
            while (iterator.hasNext()) {
                MemberCollectVO vo = iterator.next();
                if (vo.getCollectType() == NEWS) {
                    vo.setNews(newsMap.get(vo.getCollectId()));
                } else if (vo.getCollectType() == CollectType.NOTICE) {
                    vo.setNotice(noticeMap.get(vo.getCollectId()));
                } else {
                    iterator.remove();
                }
            }
        }
        return byPage.getRecords();
    }

    @Override
    public void collect(Long collectId, CollectType collectType) {
        if (!memberCollectQueryGateway.existsCollectObject(collectId, collectType)) {
            log.warn("非法收藏对象,不做任何操作 [{}] [{}]", collectId, collectType);
            return;
        }
        Long memberId = ApiHolder.getMemberId();
        MemberCollect collect = memberCollectRepository.findByMemberIdAndCollect(memberId, collectId, collectType);
        String key = String.format(MEMBER_COLLECT, collectType.getValue(), collectId);
        if (collect != null) {
            if (collect.toggle()) {
                cacheService.setHashValue(key, String.valueOf(memberId), CacheConstant.PLACE_HOLDER);
            } else {
                cacheService.deleteHashKey(key, String.valueOf(memberId));
            }
            memberCollectRepository.update(collect);
        } else {
            collect = new MemberCollect();
            collect.create(memberId, collectId, collectType, LocalDate.now());
            memberCollectRepository.save(collect);
            cacheService.setHashValue(key, String.valueOf(memberId), CacheConstant.PLACE_HOLDER);
        }
    }

    @Override
    public boolean checkCollect(Long collectId, CollectType collectType) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return false;
        }
        String key = String.format(MEMBER_COLLECT, collectType.getValue(), collectId);
        return cacheService.hasHashKey(key, String.valueOf(memberId));
    }

    @Override
    public List<CollectStatisticsVO> dayCollect(CollectRequest request) {
        List<CollectStatisticsVO> voList = memberCollectQueryGateway.dayCollect(request);
        if (request.getSelectType() == SelectType.YEAR) {
            Map<String, CollectStatisticsVO> voMap = voList.stream().collect(Collectors.toMap(CollectStatisticsVO::getCreateMonth, Function.identity()));
            return DataUtil.paddingMonth(voMap, request.getStartDate(), request.getEndDate(), CollectStatisticsVO::new);
        } else {
            Map<LocalDate, CollectStatisticsVO> voMap = voList.stream().collect(Collectors.toMap(CollectStatisticsVO::getCreateDate, Function.identity()));
            return DataUtil.paddingDay(voMap, request.getStartDate(), request.getEndDate(), CollectStatisticsVO::new);
        }
    }

    /**
     * 查询资讯信息
     *
     * @param newsIds id
     * @return 资讯信息
     */
    private Map<Long, NewsVO> getNewsMap(List<Long> newsIds) {
        if (CollUtil.isEmpty(newsIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<NewsVO> voList = memberCollectQueryGateway.listNews(newsIds);
        return voList.stream().collect(Collectors.toMap(NewsVO::getId, Function.identity()));
    }

    /**
     * 查询公告信息
     *
     * @param noticeIds id
     * @return 公告信息
     */
    private Map<Long, NoticeVO> getNoticeMap(List<Long> noticeIds) {
        if (CollUtil.isEmpty(noticeIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<NoticeVO> voList = memberCollectQueryGateway.listNotice(noticeIds);
        return voList.stream().collect(Collectors.toMap(NoticeVO::getId, Function.identity()));
    }
}
