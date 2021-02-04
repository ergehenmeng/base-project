package com.eghm.dao.mapper;

import com.eghm.dao.model.UserScoreLog;
import com.eghm.model.dto.score.UserScoreQueryDTO;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface UserScoreLogMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(UserScoreLog record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    UserScoreLog selectByPrimaryKey(Long id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(UserScoreLog record);

    /**
     * 条件查询积分列表
     * @param request 查询条件
     * @return 列表
     */
    List<UserScoreLog> getList(UserScoreQueryDTO request);
}