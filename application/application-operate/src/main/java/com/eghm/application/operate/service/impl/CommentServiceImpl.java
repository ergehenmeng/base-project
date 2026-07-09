package com.eghm.application.operate.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.dto.ext.Page;
import com.eghm.cache.CacheService;
import com.eghm.common.CommonService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.authentication.ApiHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.operate.comment.CommentDTO;
import com.eghm.dto.operate.comment.CommentQueryDTO;
import com.eghm.dto.operate.comment.CommentQueryRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.ObjectType;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.operate.model.Comment;
import com.eghm.domain.operate.model.News;
import com.eghm.domain.operate.repository.CommentRepository;
import com.eghm.application.operate.service.CommentQueryGateway;
import com.eghm.application.operate.service.CommentService;
import com.eghm.vo.operate.comment.CommentResponse;
import com.eghm.vo.operate.comment.CommentSecondVO;
import com.eghm.vo.operate.comment.CommentVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.eghm.utils.StringUtil.isNotBlank;

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

    private final SysConfigApi sysConfigApi;

    private final CacheService cacheService;

    private final CommonService commonService;

    private final CommentRepository commentRepository;

    private final CommentQueryGateway commentQueryGateway;

    @Override
    public Page<CommentResponse> listPage(CommentQueryRequest request) {
        if (isNotBlank(request.getQueryName())) {
            List<Long> objectIds = commentQueryGateway.listNewsIdsByTitle(request.getQueryName());
            if (CollUtil.isEmpty(objectIds)) {
                return new Page<>();
            }
            request.setObjectIds(objectIds);
        }
        Page<CommentResponse> page = commentQueryGateway.listPage(request.createPage(), request);
        if (CollUtil.isNotEmpty(page.getRecords())) {
            Map<ObjectType, List<Long>> collectMap = page.getRecords().stream().collect(Collectors.groupingBy(CommentResponse::getObjectType, Collectors.mapping(CommentResponse::getObjectId, Collectors.toList())));
            Map<Long, String> newsMap = commentQueryGateway.getNewsTitleMap(collectMap.get(ObjectType.NEWS));
            for (CommentResponse response : page.getRecords()) {
                if (Objects.requireNonNull(response.getObjectType()) == ObjectType.NEWS) {
                    response.setObjectName(newsMap.get(response.getObjectId()));
                }
            }
        }
        return page;
    }

    @Override
    public List<CommentVO> getByPage(CommentQueryDTO dto) {
        int reportNum = sysConfigApi.getInt(ConfigConstant.COMMENT_REPORT_SHIELD, 20);
        Page<CommentVO> voPage = commentQueryGateway.getByPage(dto, reportNum);
        List<CommentVO> records = voPage.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            Map<Long, Boolean> praiseMap = this.batchHasPraise(records.stream().map(CommentVO::getId).toList());
            records.forEach(vo -> vo.setHasPraise(praiseMap.getOrDefault(vo.getId(), false)));
        }
        return records;
    }

    @Override
    public List<CommentSecondVO> secondPage(CommentQueryDTO dto) {
        int reportNum = sysConfigApi.getInt(ConfigConstant.COMMENT_REPORT_SHIELD, 20);
        Page<CommentSecondVO> voPage = commentQueryGateway.getSecondPage(dto, reportNum);
        List<CommentSecondVO> records = voPage.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            Map<Long, Boolean> praiseMap = this.batchHasPraise(records.stream().map(CommentSecondVO::getId).toList());
            records.forEach(vo -> vo.setHasPraise(praiseMap.getOrDefault(vo.getId(), false)));
        }
        return records;
    }

    @Override
    public void add(CommentDTO dto) {
        this.checkComment(dto.getObjectId(), dto.getObjectType());
        Comment comment = new Comment();
        comment.create(dto.getMemberId(), dto.getObjectId(), dto.getObjectType(), dto.getPid(), dto.getReplyId(), dto.getContent());
        commentRepository.save(comment);
        if (dto.getPid() != null) {
            commentRepository.updateReplyNum(dto.getPid(), Comment.replyDelta(true));
        }
    }

    @Override
    public void delete(Long id, Long memberId) {
        Comment comment = commentRepository.findById(id);
        if (comment == null) {
            return;
        }
        int delete = commentRepository.deleteByIdAndMemberId(id, memberId);
        if (delete == 1 && comment.getPid() != null) {
            commentRepository.updateReplyNum(comment.getPid(), Comment.replyDelta(false));
        }
    }

    @Override
    public void praise(Long id) {
        Long memberId = ApiHolder.getMemberId();
        String key = CacheConstant.COMMENT_PRAISE + id;
        commonService.praise(key, memberId.toString(), praise -> commentRepository.updatePraiseNum(id, Comment.praiseDelta(Boolean.TRUE.equals(praise))));
    }

    @Override
    public void updateState(Long id, boolean state) {
        Comment comment = commentRepository.findById(id);
        if (state) {
            comment.unshield();
        } else {
            comment.shield();
        }
        commentRepository.updateState(comment.getId(), comment.getState());
    }

    @Override
    public void updateTopState(Long id, Integer state) {
        Comment comment = commentRepository.findById(id);
        if (Objects.equals(state, 1)) {
            comment.top();
        } else {
            comment.untop();
        }
        commentRepository.updateTopState(comment.getId(), comment.getTopState());
    }

    /**
     * 检查评论是否开启评价
     *
     * @param id         活动id或资讯id
     * @param objectType 对象类型
     */
    private void checkComment(Long id, ObjectType objectType) {
        if (objectType == ObjectType.NEWS) {
            News news = commentQueryGateway.findNewsById(id);
            if (news == null) {
                log.warn("资讯文章可能被删除,无法评价 [{}]", id);
                throw new BusinessException(ErrorCode.NEWS_NULL);
            }
            news.assertCommentSupport();
        }
    }

    /**
     * 批量判断用户是否已对评论点赞
     *
     * @param commentIds 评论id列表
     * @return map key: 评论id, value: 是否已点赞
     */
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
