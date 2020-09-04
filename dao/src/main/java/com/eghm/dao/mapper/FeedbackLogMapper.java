package com.eghm.dao.mapper;

import com.eghm.dao.model.FeedbackLog;
import com.eghm.model.vo.feedback.FeedbackVO;
import com.eghm.model.dto.feedback.FeedbackQueryRequest;

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
    List<FeedbackVO> getList(FeedbackQueryRequest request);
}