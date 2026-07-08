package com.eghm.operate.repository;

import com.eghm.operate.model.CommentReport;

/**
 * 评论举报仓储接口
 *
 * @author 二哥很猛
 * @since 2024-01-17
 */
public interface CommentReportRepository {

    CommentReport findByMemberIdAndCommentId(Long memberId, Long commentId);

    void save(CommentReport report);
}
