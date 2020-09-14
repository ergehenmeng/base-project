package com.eghm.service.user.impl;

import com.eghm.common.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.UserScoreLogMapper;
import com.eghm.dao.model.UserScoreLog;
import com.eghm.model.dto.score.UserScoreQueryDTO;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.vo.score.UserScoreVO;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.service.user.UserScoreLogService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void insertSelective(UserScoreLog scoreLog) {
        userScoreLogMapper.insertSelective(scoreLog);
    }

    @Override
    public int getSignInScore() {
        return StringUtil.random(1, sysConfigApi.getInt(ConfigConstant.SIGN_IN_SCORE));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public Paging<UserScoreVO> getByPage(UserScoreQueryDTO request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<UserScoreLog> list = userScoreLogMapper.getList(request);
        return DataUtil.convert(new PageInfo<>(list), scoreLog -> DataUtil.copy(scoreLog, UserScoreVO.class));
    }
}
