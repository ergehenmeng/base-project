package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.collect.CollectQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.business.statistics.CollectRequest;
import com.eghm.enums.SelectType;
import com.eghm.enums.ref.CollectType;
import com.eghm.mapper.MemberCollectMapper;
import com.eghm.mapper.NewsMapper;
import com.eghm.model.MemberCollect;
import com.eghm.service.business.MemberCollectService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.collect.MemberCollectVO;
import com.eghm.vo.business.news.NewsVO;
import com.eghm.vo.business.statistics.CollectStatisticsVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.constants.CacheConstant.MEMBER_COLLECT;
import static com.eghm.enums.ref.CollectType.NEWS;

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
public class MemberCollectServiceImpl implements MemberCollectService {

    private final NewsMapper newsMapper;

    private final CacheService cacheService;

    private final MemberCollectMapper memberCollectMapper;

    @Override
    public List<MemberCollectVO> getByPage(CollectQueryDTO query) {
        Page<MemberCollectVO> byPage = memberCollectMapper.getByPage(query.createPage(false), query);
        if (CollUtil.isNotEmpty(byPage.getRecords())) {
            Map<CollectType, List<Long>> collectMap = byPage.getRecords().stream().collect(Collectors.groupingBy(MemberCollectVO::getCollectType, Collectors.mapping(MemberCollectVO::getCollectId, Collectors.toList())));
            Map<Long, NewsVO> newsMap = this.getNewsMap(collectMap.get(NEWS));

            Iterator<MemberCollectVO> iterator = byPage.getRecords().iterator();
            while (iterator.hasNext()) {
                MemberCollectVO vo = iterator.next();
                if (vo.getCollectType() == NEWS) {
                    vo.setNews(newsMap.get(vo.getCollectId()));
                } else {
                    iterator.remove();
                }
            }
        }
        return byPage.getRecords();
    }

    @Override
    public void collect(Long collectId, CollectType collectType) {
        if (!this.isLegal(collectId, collectType)) {
            log.warn("非法收藏对象,不做任何操作 [{}] [{}]", collectId, collectType);
            return;
        }
        Long memberId = ApiHolder.getMemberId();
        MemberCollect collect = this.getMemberCollect(memberId, collectId, collectType);
        String key = String.format(MEMBER_COLLECT, collectType.getValue(), collectId);
        if (collect != null) {
            if (collect.getState() == 1) {
                collect.setState(0);
                cacheService.deleteHashKey(key, String.valueOf(memberId));
            } else {
                collect.setState(1);
                cacheService.setHashValue(key, String.valueOf(memberId), CacheConstant.PLACE_HOLDER);
            }
            memberCollectMapper.updateById(collect);
        } else {
            collect = new MemberCollect();
            collect.setMemberId(memberId);
            collect.setCollectId(collectId);
            collect.setCollectType(collectType);
            collect.setCreateDate(LocalDate.now());
            memberCollectMapper.insert(collect);
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
        List<CollectStatisticsVO> voList = memberCollectMapper.dayCollect(request);
        if (request.getSelectType() == SelectType.YEAR) {
            Map<String, CollectStatisticsVO> voMap = voList.stream().collect(Collectors.toMap(CollectStatisticsVO::getCreateMonth, Function.identity()));
            return DataUtil.paddingMonth(voMap, request.getStartDate(), request.getEndDate(), CollectStatisticsVO::new);
        } else {
            Map<LocalDate, CollectStatisticsVO> voMap = voList.stream().collect(Collectors.toMap(CollectStatisticsVO::getCreateDate, Function.identity()));
            return DataUtil.paddingDay(voMap, request.getStartDate(), request.getEndDate(), CollectStatisticsVO::new);
        }
    }

    /**
     * 判断收藏对象是否合法
     *
     * @param collectId 收藏对象
     * @param collectType 收藏对象类型
     * @return true:合法 false:不合法
     */
    private boolean isLegal(Long collectId, CollectType collectType) {
        if (Objects.requireNonNull(collectType) == NEWS) {
            return newsMapper.selectById(collectId) != null;
        }
        return false;
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
        List<NewsVO> voList = newsMapper.getList(newsIds);
        return voList.stream().collect(Collectors.toMap(NewsVO::getId, Function.identity()));
    }

    /**
     * 获取用户收藏记录
     *
     * @param memberId    会员id
     * @param collectId   收藏id
     * @param collectType 收藏类型
     * @return 收藏记录
     */
    private MemberCollect getMemberCollect(Long memberId, Long collectId, CollectType collectType) {
        LambdaQueryWrapper<MemberCollect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberCollect::getCollectId, collectId);
        wrapper.eq(MemberCollect::getMemberId, memberId);
        wrapper.eq(MemberCollect::getCollectType, collectType);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return memberCollectMapper.selectOne(wrapper);
    }

}
