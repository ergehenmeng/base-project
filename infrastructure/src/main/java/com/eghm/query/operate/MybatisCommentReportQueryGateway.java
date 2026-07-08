package com.eghm.query.operate;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.operate.comment.CommentReportQueryRequest;
import com.eghm.mapper.CommentReportMapper;
import com.eghm.service.operate.CommentReportQueryGateway;
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





