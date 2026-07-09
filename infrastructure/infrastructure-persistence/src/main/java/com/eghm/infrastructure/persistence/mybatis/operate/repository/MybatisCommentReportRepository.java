package com.eghm.infrastructure.persistence.mybatis.operate.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constants.CommonConstant;
import com.eghm.infrastructure.persistence.mybatis.mapper.CommentReportMapper;
import com.eghm.domain.operate.model.CommentReport;
import com.eghm.domain.operate.repository.CommentReportRepository;
import com.eghm.infrastructure.persistence.mybatis.po.CommentReportPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisCommentReportRepository implements CommentReportRepository {

    private final CommentReportMapper commentReportMapper;

    @Override
    public CommentReport findByMemberIdAndCommentId(Long memberId, Long commentId) {
        LambdaQueryWrapper<CommentReportPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(CommentReportPO::getMemberId, memberId);
        wrapper.eq(CommentReportPO::getCommentId, commentId);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return DataUtil.copy(commentReportMapper.selectOne(wrapper), CommentReport.class);
    }

    @Override
    public void save(CommentReport report) {
        commentReportMapper.insert(DataUtil.copy(report, CommentReportPO.class));
    }
}
