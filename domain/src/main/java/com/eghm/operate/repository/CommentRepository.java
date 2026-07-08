package com.eghm.operate.repository;

import com.eghm.operate.model.Comment;

/**
 * 评论仓储接口
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
public interface CommentRepository {

    Comment findById(Long id);

    void save(Comment comment);

    int deleteByIdAndMemberId(Long id, Long memberId);

    void updateReplyNum(Long id, Integer num);

    void updatePraiseNum(Long id, Integer num);

    void updateState(Long id, boolean state);

    void updateTopState(Long id, Integer state);

    void update(Comment comment);
}
