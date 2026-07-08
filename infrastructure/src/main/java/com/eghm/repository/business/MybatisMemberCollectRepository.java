package com.eghm.repository.business;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.business.model.MemberCollect;
import com.eghm.business.repository.MemberCollectRepository;
import com.eghm.constants.CommonConstant;
import com.eghm.enums.CollectType;
import com.eghm.mapper.MemberCollectMapper;
import com.eghm.po.MemberCollectPO;
import com.eghm.utils.DataUtil;
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
