package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.enums.ref.CollectType;
import com.eghm.mapper.MemberCollectMapper;
import com.eghm.model.MemberCollect;
import com.eghm.service.business.MemberCollectService;
import com.eghm.service.cache.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
