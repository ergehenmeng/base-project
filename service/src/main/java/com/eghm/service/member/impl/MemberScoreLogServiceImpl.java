package com.eghm.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.MemberScoreLogMapper;
import com.eghm.model.MemberScoreLog;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.member.MemberScoreQueryDTO;
import com.eghm.vo.member.MemberScoreVO;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.service.member.MemberScoreLogService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
@Service("memberScoreLogService")
@AllArgsConstructor
public class MemberScoreLogServiceImpl implements MemberScoreLogService {

    private final MemberScoreLogMapper memberScoreLogMapper;

    private final SysConfigApi sysConfigApi;

    @Override
    public void insert(MemberScoreLog scoreLog) {
        memberScoreLogMapper.insert(scoreLog);
    }

    @Override
    public int getSignInScore() {
        return StringUtil.random(1, sysConfigApi.getInt(ConfigConstant.SIGN_IN_SCORE));
    }

    @Override
    public PageData<MemberScoreVO> getByPage(MemberScoreQueryDTO request) {
        LambdaQueryWrapper<MemberScoreLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberScoreLog::getMemberId, request.getMemberId());
        wrapper.eq(request.getType() != null, MemberScoreLog::getType, request.getType());
        Page<MemberScoreLog> page = memberScoreLogMapper.selectPage(request.createPage(), wrapper);
        return DataUtil.copy(page, scoreLog -> DataUtil.copy(scoreLog, MemberScoreVO.class));
    }
}
