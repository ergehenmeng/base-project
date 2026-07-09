package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.comment.CommentReportDTO;
import com.eghm.application.shared.dto.operate.comment.CommentReportQueryRequest;
import com.eghm.domain.operate.model.Comment;
import com.eghm.domain.operate.model.CommentReport;
import com.eghm.domain.operate.repository.CommentReportRepository;
import com.eghm.domain.operate.repository.CommentRepository;
import com.eghm.application.operate.query.CommentReportQueryService;
import com.eghm.application.operate.service.CommentReportApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.operate.comment.CommentReportResponse;
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
public class CommentReportApplicationServiceImpl implements CommentReportApplicationService {

    private final CommentRepository commentRepository;

    private final CommentReportRepository commentReportRepository;

    private final CommentReportQueryService commentReportQueryService;

    @Override
    public Page<CommentReportResponse> getByPage(CommentReportQueryRequest request) {
        return commentReportQueryService.getByPage(request.createPage(), request);
    }

    @Override
    public void report(CommentReportDTO dto) {
        Comment comment = commentRepository.findById(dto.getCommentId());
        if (comment == null) {
            log.warn("评论信息不存在,举报失败 [{}] [{}]", dto.getCommentId(), dto.getContent());
            return;
        }
        if (!comment.canBeReportedBy(dto.getMemberId())) {
            log.warn("用户不能举报自己的评论 [{}] [{}] [{}]", dto.getMemberId(), dto.getCommentId(), dto.getContent());
            return;
        }
        CommentReport report = commentReportRepository.findByMemberIdAndCommentId(dto.getMemberId(), dto.getCommentId());
        if (report != null) {
            log.warn("用户已举报过该评论 [{}] [{}] [{}]", dto.getMemberId(), dto.getCommentId(), dto.getContent());
            return;
        }
        comment.increaseReportNum();
        commentRepository.update(comment);
        report = DataUtil.copy(dto, CommentReport.class);
        report.bindComment(comment);
        commentReportRepository.save(report);
    }
}
