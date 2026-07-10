package com.eghm.infrastructure.persistence.mybatis.member.query;

import cn.hutool.core.collection.CollUtil;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.business.collect.CollectQueryDTO;
import com.eghm.application.shared.dto.business.statistics.CollectRequest;
import com.eghm.domain.shared.enums.CollectType;
import com.eghm.domain.shared.enums.SelectType;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberCollectMapper;
import com.eghm.infrastructure.persistence.mybatis.mapper.NewsMapper;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysNoticeMapper;
import com.eghm.application.member.query.MemberCollectQueryService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.business.collect.MemberCollectVO;
import com.eghm.application.shared.vo.business.news.NewsVO;
import com.eghm.application.shared.vo.business.statistics.CollectStatisticsVO;
import com.eghm.application.shared.vo.operate.notice.NoticeVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MybatisMemberCollectQueryService implements MemberCollectQueryService {

    private final NewsMapper newsMapper;

    private final SysNoticeMapper sysNoticeMapper;

    private final MemberCollectMapper memberCollectMapper;

    @Override
    public Page<MemberCollectVO> getByPage(Page<MemberCollectVO> page, CollectQueryDTO query) {
        return MybatisPageUtil.fromMybatis(memberCollectMapper.getByPage(MybatisPageUtil.toMybatis(page), query));
    }

    @Override
    public List<MemberCollectVO> listCollectedPage(CollectQueryDTO query) {
        Page<MemberCollectVO> byPage = getByPage(query.createPage(false), query);
        if (CollUtil.isEmpty(byPage.getRecords())) {
            return byPage.getRecords();
        }
        Map<CollectType, List<Long>> collectMap = byPage.getRecords().stream().collect(Collectors.groupingBy(MemberCollectVO::getCollectType, Collectors.mapping(MemberCollectVO::getCollectId, Collectors.toList())));
        Map<Long, NewsVO> newsMap = getNewsMap(collectMap.get(CollectType.NEWS));
        Map<Long, NoticeVO> noticeMap = getNoticeMap(collectMap.get(CollectType.NOTICE));
        Iterator<MemberCollectVO> iterator = byPage.getRecords().iterator();
        while (iterator.hasNext()) {
            MemberCollectVO vo = iterator.next();
            if (vo.getCollectType() == CollectType.NEWS) {
                vo.setNews(newsMap.get(vo.getCollectId()));
            } else if (vo.getCollectType() == CollectType.NOTICE) {
                vo.setNotice(noticeMap.get(vo.getCollectId()));
            } else {
                iterator.remove();
            }
        }
        return byPage.getRecords();
    }

    private List<CollectStatisticsVO> dayCollect(CollectRequest request) {
        return memberCollectMapper.dayCollect(request);
    }

    @Override
    public List<CollectStatisticsVO> dayCollectStatistics(CollectRequest request) {
        List<CollectStatisticsVO> voList = dayCollect(request);
        if (request.getSelectType() == SelectType.YEAR) {
            Map<String, CollectStatisticsVO> voMap = voList.stream().collect(Collectors.toMap(CollectStatisticsVO::getCreateMonth, Function.identity()));
            return DataUtil.paddingMonth(voMap, request.getStartDate(), request.getEndDate(), CollectStatisticsVO::new);
        }
        Map<LocalDate, CollectStatisticsVO> voMap = voList.stream().collect(Collectors.toMap(CollectStatisticsVO::getCreateDate, Function.identity()));
        return DataUtil.paddingDay(voMap, request.getStartDate(), request.getEndDate(), CollectStatisticsVO::new);
    }

    @Override
    public boolean existsCollectObject(Long collectId, CollectType collectType) {
        if (collectType == CollectType.NEWS) {
            return newsMapper.selectById(collectId) != null;
        }
        if (collectType == CollectType.NOTICE) {
            return sysNoticeMapper.selectById(collectId) != null;
        }
        return false;
    }

    private List<NewsVO> listNews(List<Long> newsIds) {
        return newsMapper.getList(newsIds);
    }

    private List<NoticeVO> listNotice(List<Long> noticeIds) {
        return sysNoticeMapper.getList(noticeIds);
    }

    private Map<Long, NewsVO> getNewsMap(List<Long> newsIds) {
        if (CollUtil.isEmpty(newsIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<NewsVO> voList = listNews(newsIds);
        return voList.stream().collect(Collectors.toMap(NewsVO::getId, Function.identity()));
    }

    private Map<Long, NoticeVO> getNoticeMap(List<Long> noticeIds) {
        if (CollUtil.isEmpty(noticeIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<NoticeVO> voList = listNotice(noticeIds);
        return voList.stream().collect(Collectors.toMap(NoticeVO::getId, Function.identity()));
    }
}
