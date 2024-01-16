package com.eghm.service.business;

import com.eghm.dto.business.comment.CommentDTO;
import com.eghm.dto.business.comment.CommentQueryDTO;
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
     * 分页获取留言
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<CommentVO> getByPage(CommentQueryDTO dto);

    /**
     * 添加新留言
     *
     * @param dto 留言信息
     */
    void add(CommentDTO dto);

    /**
     * 点赞或取消点赞
     *
     * @param id id
     */
    void giveLike(Long id);
}
