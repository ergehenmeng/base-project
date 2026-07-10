package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.comment.CommentDTO;

/**
 * <p>
 * 评论记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
public interface CommentApplicationService {

    /**
     * 添加新留言
     *
     * @param dto 留言信息
     */
    void add(CommentDTO dto);

    /**
     * 删除评论
     *
     * @param id       id
     * @param memberId 用户id
     */
    void delete(Long id, Long memberId);

    /**
     * 点赞或取消点赞
     *
     * @param id id
     */
    void praise(Long id);

    /**
     * 屏蔽评论或取消屏蔽
     *
     * @param id    id
     * @param state false: 屏蔽 true: 显示
     */
    void updateState(Long id, boolean state);

    /**
     * 置顶状态更新
     *
     * @param id    id
     * @param state 状态
     */
    void updateTopState(Long id, Integer state);
}
