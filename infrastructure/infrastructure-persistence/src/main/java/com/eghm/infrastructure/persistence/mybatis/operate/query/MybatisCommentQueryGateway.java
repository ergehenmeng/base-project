package com.eghm.infrastructure.persistence.mybatis.operate.query;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.operate.comment.CommentQueryDTO;
import com.eghm.application.shared.dto.operate.comment.CommentQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.CommentMapper;
import com.eghm.infrastructure.persistence.mybatis.mapper.NewsMapper;
import com.eghm.domain.operate.model.News;
import com.eghm.infrastructure.persistence.mybatis.po.NewsPO;
import com.eghm.application.operate.port.out.CommentQueryGateway;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.business.news.NewsVO;
import com.eghm.application.shared.vo.operate.comment.CommentResponse;
import com.eghm.application.shared.vo.operate.comment.CommentSecondVO;
import com.eghm.application.shared.vo.operate.comment.CommentVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MybatisCommentQueryGateway implements CommentQueryGateway {

    private final NewsMapper newsMapper;

    private final CommentMapper commentMapper;

    @Override
    public Page<CommentResponse> listPage(Page<CommentResponse> page, CommentQueryRequest request) {
        return MybatisPageUtil.fromMybatis(commentMapper.listPage(MybatisPageUtil.toMybatis(page), request));
    }

    @Override
    public Page<CommentVO> getByPage(CommentQueryDTO dto, Integer reportNum) {
        return MybatisPageUtil.fromMybatis(commentMapper.getByPage(MybatisPageUtil.toMybatis(dto.createPage(false)), dto.getObjectId(), reportNum));
    }

    @Override
    public Page<CommentSecondVO> getSecondPage(CommentQueryDTO dto, Integer reportNum) {
        return MybatisPageUtil.fromMybatis(commentMapper.getSecondPage(MybatisPageUtil.toMybatis(dto.createPage(false)), dto.getObjectId(), reportNum, dto.getPid()));
    }

    @Override
    public List<Long> listNewsIdsByTitle(String queryName) {
        LambdaQueryWrapper<NewsPO> wrapper = Wrappers.lambdaQuery();
        wrapper.select(NewsPO::getId);
        wrapper.like(NewsPO::getTitle, queryName);
        return newsMapper.selectList(wrapper).stream().map(NewsPO::getId).toList();
    }

    @Override
    public Map<Long, String> getNewsTitleMap(List<Long> newsIds) {
        if (CollUtil.isEmpty(newsIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(1);
        }
        List<NewsVO> voList = newsMapper.getList(newsIds);
        return voList.stream().collect(Collectors.toMap(NewsVO::getId, NewsVO::getTitle));
    }

    @Override
    public News findNewsById(Long id) {
        return DataUtil.copy(newsMapper.selectById(id), News.class);
    }
}





