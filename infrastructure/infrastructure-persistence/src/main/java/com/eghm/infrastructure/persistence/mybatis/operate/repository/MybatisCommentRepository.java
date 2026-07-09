package com.eghm.infrastructure.persistence.mybatis.operate.repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.infrastructure.persistence.mybatis.mapper.CommentMapper;
import com.eghm.domain.operate.model.Comment;
import com.eghm.domain.operate.repository.CommentRepository;
import com.eghm.infrastructure.persistence.mybatis.po.CommentPO;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisCommentRepository implements CommentRepository {

    private final CommentMapper commentMapper;

    @Override
    public Comment findById(Long id) {
        return DataUtil.copy(commentMapper.selectById(id), Comment.class);
    }

    @Override
    public void save(Comment comment) {
        commentMapper.insert(DataUtil.copy(comment, CommentPO.class));
    }

    @Override
    public int deleteByIdAndMemberId(Long id, Long memberId) {
        LambdaUpdateWrapper<CommentPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(CommentPO::getId, id);
        wrapper.eq(CommentPO::getMemberId, memberId);
        return commentMapper.delete(wrapper);
    }

    @Override
    public void updateReplyNum(Long id, Integer num) {
        commentMapper.updateReplyNum(id, num);
    }

    @Override
    public void updatePraiseNum(Long id, Integer num) {
        commentMapper.updatePraiseNum(id, num);
    }

    @Override
    public void updateState(Long id, boolean state) {
        LambdaUpdateWrapper<CommentPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(CommentPO::getId, id);
        wrapper.set(CommentPO::getState, state);
        commentMapper.update(null, wrapper);
    }

    @Override
    public void updateTopState(Long id, Integer state) {
        LambdaUpdateWrapper<CommentPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(CommentPO::getId, id);
        wrapper.set(CommentPO::getTopState, state);
        commentMapper.update(null, wrapper);
    }

    @Override
    public void update(Comment comment) {
        commentMapper.updateById(DataUtil.copy(comment, CommentPO.class));
    }
}
