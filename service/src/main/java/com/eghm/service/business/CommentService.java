package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.comment.CommentDTO;
import com.eghm.dto.business.comment.CommentQueryDTO;
import com.eghm.dto.business.comment.CommentQueryRequest;
import com.eghm.vo.business.comment.CommentResponse;
import com.eghm.vo.business.comment.CommentSecondVO;
import com.eghm.vo.business.comment.CommentVO;

import java.util.List;

/**
 * <p>
 * 评论记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
public interface CommentService {

    /**
     * 分页获取评论列表
     *
     * @param request  查询条件
     * @return 列表
     */
    Page<CommentResponse> listPage(CommentQueryRequest request);

    /**
     * 分页获取留言 移动端 (一级评论)
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<CommentVO> getByPage(CommentQueryDTO dto);

    /**
     * 分页获取留言 移动端(二级评论)
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<CommentSecondVO> secondPage(CommentQueryDTO dto);

    /**
     * 添加新留言
     *
     * @param dto 留言信息
     */
    void add(CommentDTO dto);

    /**
     * 删除评论
     *
     * @param id  id
     * @param memberId 用户id
     */
    void delete(Long id, Long memberId);

    /**
     * 点赞或取消点赞
     *
     * @param id id
     */
    void giveLike(Long id);

    /**
     * 屏蔽评论或取消屏蔽
     *
     * @param id id
     * @param state false: 屏蔽 true: 显示
     */
    void updateState(Long id, boolean state);

    /**
     * 置顶状态更新
     *
     * @param id id
     * @param state 状态
     */
    void updateTopState(Long id, Integer state);
}
