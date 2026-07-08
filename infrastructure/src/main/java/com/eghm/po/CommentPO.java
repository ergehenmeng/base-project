package com.eghm.po;

import com.eghm.enums.ObjectType;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 评论记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
@Data
@TableName("comment")
public class CommentPO {
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
}

