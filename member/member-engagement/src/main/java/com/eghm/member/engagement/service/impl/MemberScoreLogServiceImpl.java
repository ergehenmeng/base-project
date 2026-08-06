package com.eghm.member.engagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.config.service.SysConfigApi;
import com.eghm.foundation.core.constants.ConfigConstant;
import com.eghm.member.engagement.dto.MemberScoreQueryDTO;
import com.eghm.member.engagement.dto.MemberScoreQueryRequest;
import com.eghm.member.engagement.mapper.MemberScoreLogMapper;
import com.eghm.member.engagement.entity.MemberScoreLog;
import com.eghm.member.engagement.service.MemberScoreLogService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.foundation.core.utils.StringUtil;
import com.eghm.member.engagement.vo.MemberScoreVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/5
 */
@Service
@AllArgsConstructor
public class MemberScoreLogServiceImpl implements MemberScoreLogService {

    private final SysConfigApi sysConfigApi;

    private final MemberScoreLogMapper memberScoreLogMapper;

    @Override
    public Page<MemberScoreVO> getByPage(MemberScoreQueryRequest request) {
        return memberScoreLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<MemberScoreVO> clientByPage(MemberScoreQueryDTO request) {
        LambdaQueryWrapper<MemberScoreLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberScoreLog::getMemberId, request.getMemberId());
        wrapper.eq(request.getType() != null, MemberScoreLog::getType, request.getType());
        Page<MemberScoreLog> page = memberScoreLogMapper.selectPage(request.createPage(false), wrapper);
        return DataUtil.copy(page.getRecords(), MemberScoreVO.class);
    }

    @Override
    public void insert(MemberScoreLog scoreLog) {
        memberScoreLogMapper.insert(scoreLog);
    }

    @Override
    public int getSignInScore() {
        return StringUtil.random(1, sysConfigApi.getInt(ConfigConstant.SIGN_IN_SCORE));
    }

}
