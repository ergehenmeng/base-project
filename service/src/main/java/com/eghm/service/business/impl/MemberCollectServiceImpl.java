package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.CollectType;
import com.eghm.mapper.HomestayMapper;
import com.eghm.mapper.MemberCollectMapper;
import com.eghm.mapper.ScenicMapper;
import com.eghm.model.MemberCollect;
import com.eghm.service.business.MemberCollectService;
import com.eghm.service.cache.CacheService;
import com.eghm.vo.business.collect.MemberCollectVO;
import com.eghm.vo.business.homestay.HomestayVO;
import com.eghm.vo.business.scenic.ScenicVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.constant.CacheConstant.MEMBER_COLLECT;

/**
 * <p>
 * 会员收藏记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
@AllArgsConstructor
@Service("memberCollectService")
public class MemberCollectServiceImpl implements MemberCollectService {

    private final CacheService cacheService;

    private final MemberCollectMapper memberCollectMapper;

    private final ScenicMapper scenicMapper;

    private final HomestayMapper homestayMapper;

    @Override
    public List<MemberCollectVO> getByPage(PagingQuery query) {
        Page<MemberCollectVO> byPage = memberCollectMapper.getByPage(query.createPage(false), ApiHolder.getMemberId());
        if (CollUtil.isNotEmpty(byPage.getRecords())) {
            Map<CollectType, List<Long>> collectMap = byPage.getRecords().stream().collect(Collectors.groupingBy(MemberCollectVO::getCollectType, Collectors.mapping(MemberCollectVO::getCollectId, Collectors.toList())));
            // TODO 待完善

        }
        return byPage.getRecords();
    }

    @Override
    public void collect(Long collectId, CollectType collectType) {
        Long memberId = ApiHolder.getMemberId();
        MemberCollect collect = this.getMemberCollect(memberId, collectId, collectType);
        String key = String.format(MEMBER_COLLECT, collectType.getName().toLowerCase(), collectId);
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
        String key = String.format(MEMBER_COLLECT, collectType.getName().toLowerCase(), collectId);
        return cacheService.hasHashKey(key, String.valueOf(memberId));
    }


    /**
     * 根据景区id查询景区信息
     * @param scenicIds 景区id
     * @return 景区信息
     */
    private Map<Long, ScenicVO> getScenicList(List<Long> scenicIds) {
        if (CollUtil.isEmpty(scenicIds)) {
           return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<ScenicVO> voList = scenicMapper.getList(scenicIds);
        return voList.stream().collect(Collectors.toMap(ScenicVO::getId, Function.identity()));
    }

    /**
     * 根据民宿id查询民宿信息
     * @param homestayIds 民宿id
     * @return 民宿信息
     */
    private Map<Long, HomestayVO> getHomestayList(List<Long> homestayIds) {
        if (CollUtil.isEmpty(homestayIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<HomestayVO> voList = homestayMapper.getList(homestayIds);
        return voList.stream().collect(Collectors.toMap(HomestayVO::getId, Function.identity()));
    }

    /**
     * 获取用户收藏记录
     * @param memberId 会员id
     * @param collectId 收藏id
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
