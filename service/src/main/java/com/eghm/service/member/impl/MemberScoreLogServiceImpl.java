package com.eghm.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.member.MemberScoreQueryDTO;
import com.eghm.mapper.MemberScoreLogMapper;
import com.eghm.model.MemberScoreLog;
import com.eghm.service.member.MemberScoreLogService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import com.eghm.utils.StringUtil;
import com.eghm.vo.member.MemberScoreVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/5
 */
@Service("memberScoreLogService")
@AllArgsConstructor
public class MemberScoreLogServiceImpl implements MemberScoreLogService {

    private final SysConfigApi sysConfigApi;

    private final MemberScoreLogMapper memberScoreLogMapper;

    @Override
    public List<MemberScoreVO> getByPage(MemberScoreQueryDTO request) {
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
