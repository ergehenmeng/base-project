package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Comment;
import com.eghm.vo.business.comment.CommentVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 评论记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 分页查询评论列表
     *
     * @param page      分页对象
     * @param objectId 评论对象id
     * @return 列表
     */
    Page<CommentVO> getByPage(Page<CommentVO> page, @Param("objectId") Long objectId);

    /**
     * 更新点赞数量
     *
     * @param id      id
     * @param likeNum 点赞数量
     */
    void updateLikeNum(@Param("id") Long id, @Param("likeNum") Integer likeNum);
}
