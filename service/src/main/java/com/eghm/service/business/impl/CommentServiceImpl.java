package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.business.comment.CommentDTO;
import com.eghm.dto.business.comment.CommentQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.mapper.CommentMapper;
import com.eghm.model.Comment;
import com.eghm.service.business.CommentService;
import com.eghm.service.cache.CacheService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.comment.CommentVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */

@Slf4j
@AllArgsConstructor
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    private final CacheService cacheService;

    @Override
    public List<CommentVO> getByPage(CommentQueryDTO dto) {
        Page<CommentVO> voPage = commentMapper.getByPage(dto.createPage(false), dto.getObjectId());
        voPage.getRecords().forEach(vo -> vo.setIsLiked(this.hasGiveLiked(vo.getId())));
        return voPage.getRecords();
    }

    @Override
    public void add(CommentDTO dto) {
        Comment comment = DataUtil.copy(dto, Comment.class);
        commentMapper.insert(comment);
    }

    @Override
    public void giveLike(Long id) {
        Long memberId = ApiHolder.getMemberId();
        String key = CacheConstant.COMMENT_GIVE_LIKE + id;
        boolean isLiked = cacheService.getHashValue(key, memberId.toString()) != null;
        if (isLiked) {
            cacheService.deleteHashKey(key, memberId.toString());
            commentMapper.updateLikeNum(id, -1);
        } else {
            cacheService.setHashValue(key, memberId.toString(), CacheConstant.PLACE_HOLDER);
            commentMapper.updateLikeNum(id, 1);
        }
    }

    /**
     * 判断用户是否已对文章或资讯点赞过
     *
     * @param id 文章id
     * @return true: 点赞了, 未点赞
     */
    private Boolean hasGiveLiked(Long id) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return false;
        }
        return cacheService.getHashValue(CacheConstant.COMMENT_GIVE_LIKE + id, memberId.toString()) != null;
    }
}
