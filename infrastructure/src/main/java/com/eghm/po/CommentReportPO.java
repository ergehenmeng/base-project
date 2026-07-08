package com.eghm.po;

import com.eghm.enums.ObjectType;
import com.eghm.enums.ReportType;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("comment_report")
@EqualsAndHashCode(callSuper = false)
public class CommentReportPO extends BaseEntityPO {

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

}


