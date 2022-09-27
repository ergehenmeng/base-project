package com.eghm.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.UserScoreLogMapper;
import com.eghm.model.UserScoreLog;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.score.UserScoreQueryDTO;
import com.eghm.model.vo.score.UserScoreVO;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.service.user.UserScoreLogService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
@Service("userScoreLogService")
@AllArgsConstructor
public class UserScoreLogServiceImpl implements UserScoreLogService {

    private final UserScoreLogMapper userScoreLogMapper;

    private final SysConfigApi sysConfigApi;

    @Override
    public void insert(UserScoreLog scoreLog) {
        userScoreLogMapper.insert(scoreLog);
    }

    @Override
    public int getSignInScore() {
        return StringUtil.random(1, sysConfigApi.getInt(ConfigConstant.SIGN_IN_SCORE));
    }

    @Override
    public PageData<UserScoreVO> getByPage(UserScoreQueryDTO request) {
        LambdaQueryWrapper<UserScoreLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserScoreLog::getUserId, request.getUserId());
        wrapper.eq(request.getType() != null, UserScoreLog::getType, request.getType());
        Page<UserScoreLog> page = userScoreLogMapper.selectPage(request.createPage(), wrapper);
        return DataUtil.convert(page, scoreLog -> DataUtil.copy(scoreLog, UserScoreVO.class));
    }
}
