package com.eghm.service.operate;

import com.eghm.dto.ext.Page;
import com.eghm.dto.operate.comment.CommentReportQueryRequest;
import com.eghm.vo.operate.comment.CommentReportResponse;

/**
 * 评论举报查询端口
 *
 * @author 二哥很猛
 * @since 2024-01-17
 */
public interface CommentReportQueryGateway {

    Page<CommentReportResponse> getByPage(Page<CommentReportResponse> page, CommentReportQueryRequest request);
}
