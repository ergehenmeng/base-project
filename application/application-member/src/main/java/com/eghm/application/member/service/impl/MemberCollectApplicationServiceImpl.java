package com.eghm.application.member.service.impl;

import com.eghm.domain.member.model.MemberCollect;
import com.eghm.domain.member.repository.MemberCollectRepository;
import com.eghm.application.shared.cache.CacheService;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.domain.shared.enums.CollectType;
import com.eghm.application.member.query.MemberCollectQueryService;
import com.eghm.application.member.service.MemberCollectApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.eghm.constants.CacheConstant.MEMBER_COLLECT;

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

    private final MemberCollectQueryService memberCollectQueryService;

    @Override
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

    @Override
    public boolean checkCollect(Long collectId, CollectType collectType) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return false;
        }
        String key = String.format(MEMBER_COLLECT, collectType.getValue(), collectId);
        return cacheService.hasHashKey(key, String.valueOf(memberId));
    }
}
