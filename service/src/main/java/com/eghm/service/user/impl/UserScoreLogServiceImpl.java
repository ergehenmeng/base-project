package com.eghm.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.UserScoreLogMapper;
import com.eghm.dao.model.UserScoreLog;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.score.UserScoreQueryDTO;
import com.eghm.model.vo.score.UserScoreVO;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.service.user.UserScoreLogService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
@Service("userScoreLogService")
public class UserScoreLogServiceImpl implements UserScoreLogService {

    private UserScoreLogMapper userScoreLogMapper;

    private SysConfigApi sysConfigApi;

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Autowired
    public void setUserScoreLogMapper(UserScoreLogMapper userScoreLogMapper) {
        this.userScoreLogMapper = userScoreLogMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert(UserScoreLog scoreLog) {
        userScoreLogMapper.insert(scoreLog);
    }

    @Override
    public int getSignInScore() {
        return StringUtil.random(1, sysConfigApi.getInt(ConfigConstant.SIGN_IN_SCORE));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public Paging<UserScoreVO> getByPage(UserScoreQueryDTO request) {
        LambdaQueryWrapper<UserScoreLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserScoreLog::getUserId, request.getUserId());
        wrapper.eq(request.getType() != null, UserScoreLog::getType, request.getType());
        Page<UserScoreLog> page = userScoreLogMapper.selectPage(request.createPage(), wrapper);
        return DataUtil.convert(page, scoreLog -> DataUtil.copy(scoreLog, UserScoreVO.class));
    }
}
