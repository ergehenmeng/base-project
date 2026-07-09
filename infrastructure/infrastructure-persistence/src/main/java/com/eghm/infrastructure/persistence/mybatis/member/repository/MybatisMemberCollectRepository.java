package com.eghm.infrastructure.persistence.mybatis.member.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.domain.member.model.MemberCollect;
import com.eghm.domain.member.repository.MemberCollectRepository;
import com.eghm.constants.CommonConstant;
import com.eghm.domain.shared.enums.CollectType;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberCollectMapper;
import com.eghm.infrastructure.persistence.mybatis.po.MemberCollectPO;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisMemberCollectRepository implements MemberCollectRepository {

    private final MemberCollectMapper memberCollectMapper;

    @Override
    public MemberCollect findByMemberIdAndCollect(Long memberId, Long collectId, CollectType collectType) {
        LambdaQueryWrapper<MemberCollectPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberCollectPO::getCollectId, collectId);
        wrapper.eq(MemberCollectPO::getMemberId, memberId);
        wrapper.eq(MemberCollectPO::getCollectType, collectType);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return DataUtil.copy(memberCollectMapper.selectOne(wrapper), MemberCollect.class);
    }

    @Override
    public void save(MemberCollect collect) {
        memberCollectMapper.insert(DataUtil.copy(collect, MemberCollectPO.class));
    }

    @Override
    public void update(MemberCollect collect) {
        memberCollectMapper.updateById(DataUtil.copy(collect, MemberCollectPO.class));
    }
}
