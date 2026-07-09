package com.eghm.infrastructure.persistence.mybatis.member.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.business.member.MemberScoreQueryDTO;
import com.eghm.dto.business.member.MemberScoreQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberScoreLogMapper;
import com.eghm.infrastructure.persistence.mybatis.po.MemberScoreLogPO;
import com.eghm.application.member.service.MemberScoreLogQueryGateway;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.member.MemberScoreVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis adapter for member score log read models.
 */
@Repository
@AllArgsConstructor
public class MybatisMemberScoreLogQueryGateway implements MemberScoreLogQueryGateway {

    private final MemberScoreLogMapper memberScoreLogMapper;

    @Override
    public Page<MemberScoreVO> listPage(Page<MemberScoreVO> page, MemberScoreQueryRequest request) {
        return MybatisPageUtil.fromMybatis(memberScoreLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }

    @Override
    public List<MemberScoreVO> listClientPage(MemberScoreQueryDTO request) {
        LambdaQueryWrapper<MemberScoreLogPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberScoreLogPO::getMemberId, request.getMemberId());
        wrapper.eq(request.getType() != null, MemberScoreLogPO::getType, request.getType());
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<MemberScoreLogPO> page = memberScoreLogMapper.selectPage(MybatisPageUtil.toMybatis(request.createPage(false)), wrapper);
        return DataUtil.copy(page.getRecords(), MemberScoreVO.class);
    }
}





