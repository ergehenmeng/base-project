package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.UserScoreLog;
import com.eghm.model.dto.score.UserScoreQueryDTO;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface UserScoreLogMapper extends BaseMapper<UserScoreLog> {

    /**
     * 条件查询积分列表
     * @param request 查询条件
     * @return 列表
     */
    List<UserScoreLog> getList(UserScoreQueryDTO request);
}