package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.comment.CommentDTO;
import com.eghm.dto.business.comment.CommentQueryDTO;
import com.eghm.dto.business.comment.CommentQueryRequest;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.ObjectType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ActivityMapper;
import com.eghm.mapper.CommentMapper;
import com.eghm.mapper.NewsMapper;
import com.eghm.model.Activity;
import com.eghm.model.Comment;
import com.eghm.model.News;
import com.eghm.service.business.CommentService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.activity.ActivityVO;
import com.eghm.vo.business.comment.CommentResponse;
import com.eghm.vo.business.comment.CommentSecondVO;
import com.eghm.vo.business.comment.CommentVO;
import com.eghm.vo.business.news.NewsVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */

@Slf4j
@AllArgsConstructor
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private final NewsMapper newsMapper;

    private final SysConfigApi sysConfigApi;

    private final CacheService cacheService;

    private final CommentMapper commentMapper;

    private final ActivityMapper activityMapper;

    @Override
    public Page<CommentResponse> listPage(CommentQueryRequest request) {
        if (StrUtil.isNotBlank(request.getQueryName())) {
            List<Long> objectIds = this.getObjectIds(request.getQueryName());
            if (CollUtil.isEmpty(objectIds)) {
                return new Page<>();
            }
            request.setObjectIds(objectIds);
        }
        Page<CommentResponse> page = commentMapper.listPage(request.createPage(), request);
        if (CollUtil.isNotEmpty(page.getRecords())) {
            Map<ObjectType, List<Long>> collectMap = page.getRecords().stream().collect(Collectors.groupingBy(CommentResponse::getObjectType, Collectors.mapping(CommentResponse::getObjectId, Collectors.toList())));
            Map<Long, String> newsMap = this.getNewsMap(collectMap.get(ObjectType.NEWS));
            Map<Long, String> activityMap = this.getActivityMap(collectMap.get(ObjectType.ACTIVITY));
            for (CommentResponse response : page.getRecords()) {
                switch (response.getObjectType()) {
                    case NEWS:
                        response.setObjectName(newsMap.get(response.getObjectId()));
                        break;
                    case ACTIVITY:
                        response.setObjectName(activityMap.get(response.getObjectId()));
                        break;
                    default:
                        break;
                }
            }
        }
        return page;
    }

    @Override
    public List<CommentVO> getByPage(CommentQueryDTO dto) {
        int reportNum = sysConfigApi.getInt(ConfigConstant.COMMENT_REPORT_SHIELD, 20);
        Page<CommentVO> voPage = commentMapper.getByPage(dto.createPage(false), dto.getObjectId(), reportNum);
        voPage.getRecords().forEach(vo -> vo.setHasPraise(hasPraise(vo.getId())));
        return voPage.getRecords();
    }

    @Override
    public List<CommentSecondVO> secondPage(CommentQueryDTO dto) {
        int reportNum = sysConfigApi.getInt(ConfigConstant.COMMENT_REPORT_SHIELD, 20);
        Page<CommentSecondVO> voPage = commentMapper.getSecondPage(dto.createPage(false), dto.getObjectId(), reportNum, dto.getPid());
        voPage.getRecords().forEach(vo -> vo.setHasPraise(hasPraise(vo.getId())));
        return voPage.getRecords();
    }

    @Override
    public void add(CommentDTO dto) {
        this.checkComment(dto.getObjectId(), dto.getObjectType());
        Comment comment = DataUtil.copy(dto, Comment.class);
        comment.setGrade(comment.getReplyId() == null ? 1 : 2);
        commentMapper.insert(comment);
        if (dto.getPid() != null) {
            commentMapper.updateReplyNum(dto.getPid(), 1);
        }
    }

    @Override
    public void delete(Long id, Long memberId) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            return;
        }
        LambdaUpdateWrapper<Comment> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Comment::getId, id);
        wrapper.eq(Comment::getMemberId, memberId);
        int delete = commentMapper.delete(wrapper);
        if (delete == 1 && comment.getPid() != null) {
            commentMapper.updateReplyNum(comment.getPid(), -1);
        }
    }

    @Override
    public void giveLike(Long id) {
        Long memberId = ApiHolder.getMemberId();
        String key = CacheConstant.COMMENT_PRAISE + id;
        boolean hasPraise = cacheService.getHashValue(key, memberId.toString()) != null;
        if (hasPraise) {
            cacheService.deleteHashKey(key, memberId.toString());
            commentMapper.updatePraiseNum(id, -1);
        } else {
            cacheService.setHashValue(key, memberId.toString(), CacheConstant.PLACE_HOLDER);
            commentMapper.updatePraiseNum(id, 1);
        }
    }

    @Override
    public void updateState(Long id, boolean state) {
        LambdaUpdateWrapper<Comment> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Comment::getId, id);
        wrapper.set(Comment::getState, state);
        commentMapper.update(null, wrapper);
    }

    @Override
    public void updateTopState(Long id, Integer state) {
        LambdaUpdateWrapper<Comment> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Comment::getId, id);
        wrapper.set(Comment::getTopState, state);
        commentMapper.update(null, wrapper);
    }

    /**
     * 检查评论是否开启评价
     *
     * @param id 活动id或资讯id
     * @param objectType 对象类型
     */
    private void checkComment(Long id, ObjectType objectType) {
        if (objectType == ObjectType.NEWS) {
            News news = newsMapper.selectById(id);
            if (news == null) {
                log.warn("资讯文章可能被删除,无法评价 [{}]", id);
                throw new BusinessException(ErrorCode.NEWS_NULL);
            }
            if (Boolean.FALSE.equals(news.getCommentSupport())) {
                log.warn("资讯文章未开启评论 [{}]", id);
                throw new BusinessException(ErrorCode.NEWS_COMMENT_FORBID);
            }
        }
        if (objectType == ObjectType.ACTIVITY) {
            Activity activity = activityMapper.selectById(id);
            if (activity == null) {
                log.warn("活动可能被删除,无法评价 [{}]", id);
                throw new BusinessException(ErrorCode.ACTIVITY_NULL);
            }
            if (Boolean.FALSE.equals(activity.getCommentSupport())) {
                log.warn("活动未开启评论 [{}]", id);
                throw new BusinessException(ErrorCode.ACTIVITY_COMMENT_FORBID);
            }
        }

    }

    /**
     * 根据对象名称获取对象id
     *
     * @param queryName 对象名称
     * @return 对象id
     */
    private List<Long> getObjectIds(String queryName) {
        LambdaQueryWrapper<News> wrapper = Wrappers.lambdaQuery();
        wrapper.select(News::getId);
        wrapper.like(News::getTitle, queryName);
        List<Long> objectIds = newsMapper.selectList(wrapper).stream().map(News::getId).collect(Collectors.toList());
        LambdaQueryWrapper<Activity> activityWrapper = Wrappers.lambdaQuery();
        activityWrapper.select(Activity::getId);
        activityWrapper.like(Activity::getTitle, queryName);
        List<Long> activityIds = activityMapper.selectList(activityWrapper).stream().map(Activity::getId).collect(Collectors.toList());
        objectIds.addAll(activityIds);
        return objectIds;
    }

    /**
     * 获取资讯标题
     *
     * @param newsIds 新闻id
     * @return 资讯信息
     */
    private Map<Long, String> getNewsMap(List<Long> newsIds) {
        if (CollUtil.isEmpty(newsIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(1);
        }
        List<NewsVO> voList = newsMapper.getList(newsIds);
        return voList.stream().collect(Collectors.toMap(NewsVO::getId, NewsVO::getTitle));
    }

    /**
     * 获取活动标题
     *
     * @param activityIds 活动id
     * @return 活动信息
     */
    private Map<Long, String> getActivityMap(List<Long> activityIds) {
        if (CollUtil.isEmpty(activityIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(1);
        }
        List<ActivityVO> voList = activityMapper.getList(activityIds);
        return voList.stream().collect(Collectors.toMap(ActivityVO::getId, ActivityVO::getTitle));
    }

    /**
     * 判断用户是否已对文章或资讯点赞过
     *
     * @param id 文章id
     * @return true: 点赞了, 未点赞
     */
    private Boolean hasPraise(Long id) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return false;
        }
        return cacheService.getHashValue(CacheConstant.COMMENT_PRAISE + id, memberId.toString()) != null;
    }
}
