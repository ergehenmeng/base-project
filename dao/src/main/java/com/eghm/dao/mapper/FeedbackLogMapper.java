package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.FeedbackLog;
import com.eghm.model.dto.feedback.FeedbackQueryRequest;
import com.eghm.model.vo.feedback.FeedbackVO;

public interface FeedbackLogMapper extends BaseMapper<FeedbackLog> {

    /**
     * 根据条件查询反馈列表
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<FeedbackVO> getByPage(Page<FeedbackVO> page, FeedbackQueryRequest request);
}