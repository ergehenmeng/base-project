package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.FeedbackLog;
import com.eghm.model.vo.feedback.FeedbackVO;
import com.eghm.model.dto.feedback.FeedbackQueryRequest;

import java.util.List;

public interface FeedbackLogMapper extends BaseMapper<FeedbackLog> {

    /**
     * 根据条件查询反馈列表
     * @param request 查询条件
     * @return 列表
     */
    List<FeedbackVO> getList(FeedbackQueryRequest request);
}