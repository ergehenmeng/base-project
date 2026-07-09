package com.eghm.application.operate.port.out;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.comment.CommentReportQueryRequest;
import com.eghm.application.shared.vo.operate.comment.CommentReportResponse;

/**
 * 评论举报查询端口
 *
 * @author 二哥很猛
 * @since 2024-01-17
 */
public interface CommentReportQueryGateway {

    Page<CommentReportResponse> getByPage(Page<CommentReportResponse> page, CommentReportQueryRequest request);
}
