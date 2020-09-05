package com.eghm.service.user;

import com.eghm.dao.model.UserScoreLog;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
public interface UserScoreLogService {

    /**
     * 添加积分信息
     * @param scoreLog 积分
     */
    void insertSelective(UserScoreLog scoreLog);

}
