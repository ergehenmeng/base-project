package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.operate.comment.CommentReportQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.CommentReportMapper;
import com.eghm.application.operate.query.CommentReportQueryService;
import com.eghm.application.shared.vo.operate.comment.CommentReportResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisCommentReportQueryService implements CommentReportQueryService {

    private final CommentReportMapper commentReportMapper;

    @Override
    public Page<CommentReportResponse> getByPage(Page<CommentReportResponse> page, CommentReportQueryRequest request) {
        return MybatisPageUtil.fromMybatis(commentReportMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





