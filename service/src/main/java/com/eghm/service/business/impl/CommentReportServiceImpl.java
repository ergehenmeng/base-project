package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.comment.CommentReportDTO;
import com.eghm.dto.business.comment.CommentReportQueryRequest;
import com.eghm.mapper.CommentMapper;
import com.eghm.mapper.CommentReportMapper;
import com.eghm.model.Comment;
import com.eghm.model.CommentReport;
import com.eghm.service.business.CommentReportService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.comment.CommentReportResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论举报记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-17
 */

@Slf4j
@AllArgsConstructor
@Service("commentReportService")
public class CommentReportServiceImpl implements CommentReportService {

    private final CommentMapper commentMapper;

    private final CommentReportMapper commentReportMapper;

    @Override
    public Page<CommentReportResponse> getByPage(CommentReportQueryRequest request) {
        return commentReportMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void report(CommentReportDTO dto) {
        Comment comment = commentMapper.selectById(dto.getCommentId());
        if (comment == null) {
            log.warn("评论信息不存在,举报失败 [{}] [{}]", dto.getCommentId(), dto.getContent());
            return;
        }
        if (comment.getMemberId().equals(dto.getMemberId())) {
            log.warn("用户不能举报自己的评论 [{}] [{}] [{}]", dto.getMemberId(), dto.getCommentId(), dto.getContent());
            return;
        }
        CommentReport report = this.getReportComment(dto.getMemberId(), dto.getCommentId());
        if (report != null) {
            log.warn("用户已举报过该评论 [{}] [{}] [{}]", dto.getMemberId(), dto.getCommentId(), dto.getContent());
            return;
        }
        comment.setReportNum(comment.getReportNum() + 1);
        commentMapper.updateById(comment);

        report = DataUtil.copy(dto, CommentReport.class);
        report.setObjectId(comment.getObjectId());
        report.setObjectType(comment.getObjectType());
        commentReportMapper.insert(report);
    }

    /**
     * 获取用户的评论举报记录
     *
     * @param memberId 用户id
     * @param commentId 评论id
     * @return 评论举报记录
     */
    private CommentReport getReportComment(Long memberId, Long commentId) {
        LambdaQueryWrapper<CommentReport> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(CommentReport::getMemberId, memberId);
        wrapper.eq(CommentReport::getCommentId, commentId);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return commentReportMapper.selectOne(wrapper);
    }
}
