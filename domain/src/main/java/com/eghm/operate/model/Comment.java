package com.eghm.operate.model;

import com.eghm.enums.ObjectType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 评论记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
@Data
public class Comment {
    /** id主键 */
    private Long id;

    /** 用户ID */
    private Long memberId;

    /** 评论对象ID */
    private Long objectId;

    /** 状态 0:已屏蔽 1:正常 */
    private Boolean state;

    /** 置顶状态 0:未置顶 1:置顶 */
    private Integer topState;

    /** 评论对象类型 (1:资讯 2:活动) */
    private ObjectType objectType;

    /** 点赞数量 */
    private Integer praiseNum;

    /** 回复id */
    private Long replyId;

    /** 评论信息 */
    private String content;

    /** 被举报次数 */
    private Integer reportNum;

    /** 父评论 */
    private Long pid;

    /** 评论级别 1:一级评论 2:二级评论 */
    private Integer grade;

    /** 被评论次数 */
    private Integer replyNum;

    /** 添加时间 */
    private LocalDateTime createTime;
    
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 是否已删除 0:未删除 1:已删除 */
    private Boolean deleted;

    public void create(Long memberId, Long objectId, ObjectType objectType, Long pid, Long replyId, String content) {
        this.memberId = memberId;
        this.objectId = objectId;
        this.objectType = objectType;
        this.pid = pid;
        this.replyId = replyId;
        this.content = content;
        this.grade = replyId == null ? 1 : 2;
    }

    /**
     * 是否允许指定会员举报.
     *
     * @param reportMemberId 举报会员id
     * @return true: 允许举报
     */
    public boolean canBeReportedBy(Long reportMemberId) {
        return !Objects.equals(this.memberId, reportMemberId);
    }

    /**
     * 增加举报次数.
     */
    public void increaseReportNum() {
        this.reportNum = this.reportNum == null ? 1 : this.reportNum + 1;
    }

    public void shield() {
        this.state = false;
    }

    public void unshield() {
        this.state = true;
    }

    public void top() {
        this.topState = 1;
    }

    public void untop() {
        this.topState = 0;
    }

    public void increasePraiseNum() {
        this.praiseNum = this.praiseNum == null ? 1 : this.praiseNum + 1;
    }

    public void decreasePraiseNum() {
        this.praiseNum = this.praiseNum == null ? 0 : this.praiseNum - 1;
    }

    public void increaseReplyNum() {
        this.replyNum = this.replyNum == null ? 1 : this.replyNum + 1;
    }

    public void decreaseReplyNum() {
        this.replyNum = this.replyNum == null ? 0 : this.replyNum - 1;
    }

    public boolean isShielded() {
        return Boolean.FALSE.equals(this.state);
    }
}
