package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.common.CommonService;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.application.shared.dto.operate.comment.CommentDTO;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.ObjectType;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.operate.model.Comment;
import com.eghm.domain.operate.model.News;
import com.eghm.domain.operate.repository.CommentRepository;
import com.eghm.application.operate.query.CommentQueryService;
import com.eghm.application.operate.service.CommentApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
public class CommentApplicationServiceImpl implements CommentApplicationService {

    private final CommonService commonService;

    private final CommentRepository commentRepository;

    private final CommentQueryService commentQueryService;

    @Override
    public void add(CommentDTO dto) {
        this.checkComment(dto.getObjectId(), dto.getObjectType());
        Comment comment = new Comment();
        comment.create(dto.getMemberId(), dto.getObjectId(), dto.getObjectType(), dto.getPid(), dto.getReplyId(), dto.getContent());
        commentRepository.save(comment);
        if (dto.getPid() != null) {
            commentRepository.updateReplyNum(dto.getPid(), Comment.replyDelta(true));
        }
    }

    @Override
    public void delete(Long id, Long memberId) {
        Comment comment = commentRepository.findById(id);
        if (comment == null) {
            return;
        }
        int delete = commentRepository.deleteByIdAndMemberId(id, memberId);
        if (delete == 1 && comment.getPid() != null) {
            commentRepository.updateReplyNum(comment.getPid(), Comment.replyDelta(false));
        }
    }

    @Override
    public void praise(Long id) {
        Long memberId = ApiHolder.getMemberId();
        String key = CacheConstant.COMMENT_PRAISE + id;
        commonService.praise(key, memberId.toString(), praise -> commentRepository.updatePraiseNum(id, Comment.praiseDelta(Boolean.TRUE.equals(praise))));
    }

    @Override
    public void updateState(Long id, boolean state) {
        Comment comment = commentRepository.findById(id);
        if (state) {
            comment.unshield();
        } else {
            comment.shield();
        }
        commentRepository.updateState(comment.getId(), comment.getState());
    }

    @Override
    public void updateTopState(Long id, Integer state) {
        Comment comment = commentRepository.findById(id);
        if (Objects.equals(state, 1)) {
            comment.top();
        } else {
            comment.untop();
        }
        commentRepository.updateTopState(comment.getId(), comment.getTopState());
    }

    /**
     * 检查评论是否开启评价
     *
     * @param id         活动id或资讯id
     * @param objectType 对象类型
     */
    private void checkComment(Long id, ObjectType objectType) {
        if (objectType == ObjectType.NEWS) {
            News news = commentQueryService.findNewsById(id);
            if (news == null) {
                log.warn("资讯文章可能被删除,无法评价 [{}]", id);
                throw new BusinessException(ErrorCode.NEWS_NULL);
            }
            news.assertCommentSupport();
        }
    }

}
