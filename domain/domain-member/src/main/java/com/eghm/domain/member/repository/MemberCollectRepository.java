package com.eghm.domain.member.repository;

import com.eghm.domain.member.model.MemberCollect;
import com.eghm.domain.shared.enums.CollectType;

/**
 * 会员收藏仓储接口
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
public interface MemberCollectRepository {

    MemberCollect findByMemberIdAndCollect(Long memberId, Long collectId, CollectType collectType);

    void save(MemberCollect collect);

    void update(MemberCollect collect);
}
