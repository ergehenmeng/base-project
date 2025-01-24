package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.comment.CommentQueryRequest;
import com.eghm.model.Comment;
import com.eghm.vo.operate.comment.CommentResponse;
import com.eghm.vo.operate.comment.CommentSecondVO;
import com.eghm.vo.operate.comment.CommentVO;
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
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<CommentResponse> listPage(Page<CommentResponse> page, @Param("param") CommentQueryRequest request);

    /**
     * 分页查询评论列表 某个对象的评论列表 (移动端)
     *
     * @param page      分页对象
     * @param objectId  评论对象id
     * @param shieldNum 举报屏蔽次数
     * @return 列表
     */
    Page<CommentVO> getByPage(Page<CommentSecondVO> page, @Param("objectId") Long objectId, @Param("shieldNum") Integer shieldNum);

    /**
     * 分页查询评论列表 某个对象的评论列表 (移动端)
     *
     * @param page      分页对象
     * @param objectId  评论对象id
     * @param shieldNum 举报屏蔽次数
     * @param pid       父评论ID
     * @return 列表
     */
    Page<CommentSecondVO> getSecondPage(Page<CommentSecondVO> page, @Param("objectId") Long objectId, @Param("shieldNum") Integer shieldNum, @Param("pid") Long pid);

    /**
     * 更新点赞数量
     *
     * @param id        id
     * @param praiseNum 点赞数量
     */
    void updatePraiseNum(@Param("id") Long id, @Param("praiseNum") Integer praiseNum);

    /**
     * 更新评论回复数量
     *
     * @param id  id
     * @param num 回复数量
     */
    void updateReplyNum(@Param("id") Long id, @Param("num") Integer num);
}
