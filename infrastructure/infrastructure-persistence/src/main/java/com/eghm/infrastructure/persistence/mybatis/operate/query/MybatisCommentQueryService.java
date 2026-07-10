package com.eghm.infrastructure.persistence.mybatis.operate.query;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.application.shared.cache.CacheService;
import com.eghm.application.shared.common.SysConfigService;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.operate.comment.CommentQueryDTO;
import com.eghm.application.shared.dto.operate.comment.CommentQueryRequest;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.domain.shared.enums.ObjectType;
import com.eghm.infrastructure.persistence.mybatis.mapper.CommentMapper;
import com.eghm.infrastructure.persistence.mybatis.mapper.NewsMapper;
import com.eghm.domain.operate.model.News;
import com.eghm.infrastructure.persistence.mybatis.po.NewsPO;
import com.eghm.application.operate.query.CommentQueryService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.business.news.NewsVO;
import com.eghm.application.shared.vo.operate.comment.CommentResponse;
import com.eghm.application.shared.vo.operate.comment.CommentSecondVO;
import com.eghm.application.shared.vo.operate.comment.CommentVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.application.shared.utils.StringUtil.isNotBlank;

@Repository
@AllArgsConstructor
public class MybatisCommentQueryService implements CommentQueryService {

    private final SysConfigService sysConfigService;

    private final CacheService cacheService;

    private final NewsMapper newsMapper;

    private final CommentMapper commentMapper;

    @Override
    public Page<CommentResponse> listPage(Page<CommentResponse> page, CommentQueryRequest request) {
        return MybatisPageUtil.fromMybatis(commentMapper.listPage(MybatisPageUtil.toMybatis(page), request));
    }

    @Override
    public Page<CommentResponse> listManagePage(CommentQueryRequest request) {
        if (isNotBlank(request.getQueryName())) {
            List<Long> objectIds = listNewsIdsByTitle(request.getQueryName());
            if (CollUtil.isEmpty(objectIds)) {
                return new Page<>();
            }
            request.setObjectIds(objectIds);
        }
        Page<CommentResponse> page = listPage(request.createPage(), request);
        if (CollUtil.isNotEmpty(page.getRecords())) {
            Map<ObjectType, List<Long>> collectMap = page.getRecords().stream().collect(Collectors.groupingBy(CommentResponse::getObjectType, Collectors.mapping(CommentResponse::getObjectId, Collectors.toList())));
            Map<Long, String> newsMap = getNewsTitleMap(collectMap.get(ObjectType.NEWS));
            for (CommentResponse response : page.getRecords()) {
                if (Objects.requireNonNull(response.getObjectType()) == ObjectType.NEWS) {
                    response.setObjectName(newsMap.get(response.getObjectId()));
                }
            }
        }
        return page;
    }

    @Override
    public Page<CommentVO> getByPage(CommentQueryDTO dto, Integer reportNum) {
        return MybatisPageUtil.fromMybatis(commentMapper.getByPage(MybatisPageUtil.toMybatis(dto.createPage(false)), dto.getObjectId(), reportNum));
    }

    @Override
    public List<CommentVO> listClientPage(CommentQueryDTO dto) {
        int reportNum = sysConfigService.getInt(ConfigConstant.COMMENT_REPORT_SHIELD, 20);
        Page<CommentVO> voPage = getByPage(dto, reportNum);
        List<CommentVO> records = voPage.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            Map<Long, Boolean> praiseMap = batchHasPraise(records.stream().map(CommentVO::getId).toList());
            records.forEach(vo -> vo.setHasPraise(praiseMap.getOrDefault(vo.getId(), false)));
        }
        return records;
    }

    @Override
    public Page<CommentSecondVO> getSecondPage(CommentQueryDTO dto, Integer reportNum) {
        return MybatisPageUtil.fromMybatis(commentMapper.getSecondPage(MybatisPageUtil.toMybatis(dto.createPage(false)), dto.getObjectId(), reportNum, dto.getPid()));
    }

    @Override
    public List<CommentSecondVO> listSecondClientPage(CommentQueryDTO dto) {
        int reportNum = sysConfigService.getInt(ConfigConstant.COMMENT_REPORT_SHIELD, 20);
        Page<CommentSecondVO> voPage = getSecondPage(dto, reportNum);
        List<CommentSecondVO> records = voPage.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            Map<Long, Boolean> praiseMap = batchHasPraise(records.stream().map(CommentSecondVO::getId).toList());
            records.forEach(vo -> vo.setHasPraise(praiseMap.getOrDefault(vo.getId(), false)));
        }
        return records;
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

    private Map<Long, Boolean> batchHasPraise(List<Long> commentIds) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            Map<Long, Boolean> emptyMap = new HashMap<>(commentIds.size());
            commentIds.forEach(id -> emptyMap.put(id, false));
            return emptyMap;
        }
        List<String> keys = commentIds.stream().map(id -> CacheConstant.COMMENT_PRAISE + id).toList();
        Map<String, Boolean> keyResultMap = cacheService.batchHasHashKey(keys, memberId.toString());
        Map<Long, Boolean> resultMap = new HashMap<>(commentIds.size());
        for (Long commentId : commentIds) {
            String key = CacheConstant.COMMENT_PRAISE + commentId;
            resultMap.put(commentId, keyResultMap.getOrDefault(key, false));
        }
        return resultMap;
    }
}
