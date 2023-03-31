package com.eghm.service.user;

import com.eghm.model.UserScoreLog;
import com.eghm.dto.score.UserScoreQueryDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.vo.score.UserScoreVO;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
public interface UserScoreLogService {

    /**
     * 添加积分信息
     * @param scoreLog 积分
     */
    void insert(UserScoreLog scoreLog);

    /**
     * 获取每日签到积分数 (随机,且由系统参数影响)
     * @return 积分数
     */
    int getSignInScore();

    /**
     * 分页查询用户积分列表
     * @param request 查询条件
     * @return 积分列表
     */
    PageData<UserScoreVO> getByPage(UserScoreQueryDTO request);
}
