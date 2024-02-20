package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.comment.CommentQueryRequest;
import com.eghm.model.Comment;
import com.eghm.vo.business.comment.CommentResponse;
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
     * 分页查询评论列表 (管理后台)
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<CommentResponse> listPage(Page<CommentResponse> page, @Param("param") CommentQueryRequest request);

    /**
     * 分页查询评论列表 某个对象的评论列表 (移动端)
     *
     * @param page      分页对象
     * @param objectId 评论对象id
     * @param shieldNum 举报屏蔽次数
     * @return 列表
     */
    Page<CommentVO> getByPage(Page<CommentVO> page, @Param("objectId") Long objectId, @Param("shieldNum") Integer shieldNum);

    /**
     * 更新点赞数量
     *
     * @param id      id
     * @param likeNum 点赞数量
     */
    void updateLikeNum(@Param("id") Long id, @Param("likeNum") Integer likeNum);
}
