package com.eghm.infrastructure.persistence.mybatis.member.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberNoticeMapper;
import com.eghm.infrastructure.persistence.mybatis.po.MemberNoticePO;
import com.eghm.application.member.service.MemberNoticeQueryGateway;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.member.MemberNoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisMemberNoticeQueryGateway implements MemberNoticeQueryGateway {

    private final MemberNoticeMapper memberNoticeMapper;

    @Override
    public List<MemberNoticeVO> getByPage(Page<MemberNoticeVO> page, Long memberId) {
        LambdaQueryWrapper<MemberNoticePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberNoticePO::getMemberId, memberId);
        wrapper.last(" order by is_read desc, id desc ");
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<MemberNoticePO> poPage = MybatisPageUtil.toMybatis(new Page<>(page.getCurrent(), page.getSize(), page.getTotal()));
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<MemberNoticePO> selectedPage = memberNoticeMapper.selectPage(poPage, wrapper);
        return DataUtil.copy(selectedPage.getRecords(), MemberNoticeVO.class);
    }

    @Override
    public Long countUnRead(Long memberId) {
        LambdaQueryWrapper<MemberNoticePO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberNoticePO::getMemberId, memberId);
        wrapper.eq(MemberNoticePO::getIsRead, false);
        return memberNoticeMapper.selectCount(wrapper);
    }
}

