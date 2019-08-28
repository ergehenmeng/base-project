package com.fanyin.dao.mapper.business;

import com.fanyin.dao.model.business.FeedbackLog;
import com.fanyin.model.dto.business.feedback.FeedbackModel;
import com.fanyin.model.dto.business.feedback.FeedbackQueryRequest;

import java.util.List;

public interface FeedbackLogMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(FeedbackLog record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    FeedbackLog selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(FeedbackLog record);

    /**
     * 根据条件查询反馈列表
     * @param request 查询条件
     * @return 列表
     */
    List<FeedbackModel> getList(FeedbackQueryRequest request);
}