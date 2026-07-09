package com.eghm.operate.model;

import com.eghm.model.BaseEntity;

import com.eghm.enums.ObjectType;
import com.eghm.enums.ReportType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评论举报记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommentReport extends BaseEntity {

    /** 举报用户ID */
    private Long memberId;

    /** 评价ID */
    private Long commentId;

    /** 评论对象ID */
    private Long objectId;

    /** 评论对象类型 (1:资讯 2:活动) */
    private ObjectType objectType;

    /** 举报类型 (1:淫秽色情 2:营销广告 3:违法信息 4:网络暴力 5:虚假谣言 6:养老诈骗 7:其他) */
    private ReportType reportType;

    /** 举报内容 */
    private String content;

    /** 处理状态 0:待处理 1:已处理 2:已忽略 */
    private Integer state;

    /**
     * 绑定被举报评论的信息.
     *
     * @param comment 被举报评论
     */
    public void bindComment(Comment comment) {
        this.objectId = comment.getObjectId();
        this.objectType = comment.getObjectType();
    }

    public void initialize(Long memberId, Long commentId, ReportType reportType, String content) {
        this.memberId = memberId;
        this.commentId = commentId;
        this.reportType = reportType;
        this.content = content;
        this.state = 0;
    }

    public void process() {
        this.state = 1;
    }

    public void ignore() {
        this.state = 2;
    }

    public boolean isPending() {
        return Integer.valueOf(0).equals(this.state);
    }
}
