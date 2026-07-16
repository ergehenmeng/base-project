package com.eghm.business.operation.interaction.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.business.operation.interaction.dto.CommentReportDTO;
import com.eghm.business.operation.interaction.dto.CommentReportQueryRequest;
import com.eghm.business.operation.interaction.vo.CommentReportResponse;

/**
 * <p>
 * 评论举报记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-17
 */
public interface CommentReportService {

    /**
     * 获取评论举报记录
     *
     * @param request 查询参数
     * @return 列表
     */
    Page<CommentReportResponse> getByPage(CommentReportQueryRequest request);

    /**
     * 举报评论
     *
     * @param dto 举报信息
     */
    void report(CommentReportDTO dto);
}
