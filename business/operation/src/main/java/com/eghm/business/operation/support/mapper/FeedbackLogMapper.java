package com.eghm.business.operation.support.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.business.operation.support.dto.FeedbackQueryRequest;
import com.eghm.business.operation.support.entity.FeedbackLog;
import com.eghm.business.operation.support.vo.FeedbackResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author eghm
 */
public interface FeedbackLogMapper extends BaseMapper<FeedbackLog> {

    /**
     * 根据条件查询反馈列表
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<FeedbackResponse> getByPage(Page<FeedbackResponse> page, @Param("param") FeedbackQueryRequest request);
}