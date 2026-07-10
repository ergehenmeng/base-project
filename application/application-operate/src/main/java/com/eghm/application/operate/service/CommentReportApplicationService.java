package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.comment.CommentReportDTO;

/**
 * <p>
 * 评论举报记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-17
 */
public interface CommentReportApplicationService {

    /**
     * 举报评论
     *
     * @param dto 举报信息
     */
    void report(CommentReportDTO dto);
}
