package com.eghm.application.member.service;

import com.eghm.application.member.query.MemberCollectQueryService;
import com.eghm.application.shared.cache.CacheService;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.domain.member.model.MemberCollect;
import com.eghm.domain.member.repository.MemberCollectRepository;
import com.eghm.domain.shared.enums.CollectType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.eghm.constants.CacheConstant.MEMBER_COLLECT;

/**
 * <p>
 * 会员收藏记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
@Slf4j
@Service
@AllArgsConstructor
public class MemberCollectApplicationService {
    
    private final CacheService cacheService;
    
    private final MemberCollectRepository memberCollectRepository;
    
    private final MemberCollectQueryService memberCollectQueryService;
    
    /**
     * 收藏或取消收藏
     *
     * @param collectId   收藏对象id
     * @param collectType 收藏对象类型
     */
    public void collect(Long collectId, CollectType collectType) {
        if (!memberCollectQueryService.existsCollectObject(collectId, collectType)) {
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

    /**
     * 检查会员是否已经收藏该对象, 如果用户未登录,默认没有收藏
     *
     * @param collectId   对象id
     * @param collectType 对象类型
     * @return false : 未收藏 true : 已收藏
     */
    public boolean checkCollect(Long collectId, CollectType collectType) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return false;
        }
        String key = String.format(MEMBER_COLLECT, collectType.getValue(), collectId);
        return cacheService.hasHashKey(key, String.valueOf(memberId));
    }

}
