package com.eghm.query.operate;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.operate.comment.CommentQueryDTO;
import com.eghm.dto.operate.comment.CommentQueryRequest;
import com.eghm.mapper.CommentMapper;
import com.eghm.mapper.NewsMapper;
import com.eghm.operate.model.News;
import com.eghm.po.NewsPO;
import com.eghm.service.operate.CommentQueryGateway;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.news.NewsVO;
import com.eghm.vo.operate.comment.CommentResponse;
import com.eghm.vo.operate.comment.CommentSecondVO;
import com.eghm.vo.operate.comment.CommentVO;
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





