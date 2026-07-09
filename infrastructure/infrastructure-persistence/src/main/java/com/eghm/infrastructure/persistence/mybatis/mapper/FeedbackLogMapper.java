package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.application.shared.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.po.FeedbackLogPO;
import com.eghm.application.shared.vo.operate.feedback.FeedbackResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author eghm
 */
public interface FeedbackLogMapper extends BaseMapper<FeedbackLogPO> {

    /**
     * 根据条件查询反馈列表
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<FeedbackResponse> getByPage(Page<FeedbackResponse> page, @Param("param") FeedbackQueryRequest request);
}
