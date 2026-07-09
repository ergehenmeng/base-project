package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.operate.comment.CommentReportQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.CommentReportMapper;
import com.eghm.application.operate.service.CommentReportQueryGateway;
import com.eghm.vo.operate.comment.CommentReportResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisCommentReportQueryGateway implements CommentReportQueryGateway {

    private final CommentReportMapper commentReportMapper;

    @Override
    public Page<CommentReportResponse> getByPage(Page<CommentReportResponse> page, CommentReportQueryRequest request) {
        return MybatisPageUtil.fromMybatis(commentReportMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





