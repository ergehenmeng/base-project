package com.eghm.business.model;

import com.eghm.common.model.BaseEntity;

import com.eghm.enums.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户站内信
 *
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberNotice extends BaseEntity {

    /** 用户id */
    private Long memberId;

    /** 消息标题 */
    private String title;

    /** 站内信内容 */
    private String content;

    /** 站内信分类 */
    private MessageType messageType;

    /** 状态 0:未读 1:已读 */
    private Boolean isRead;

    /** 消息所属日志id */
    private Long noticeLogId;

}
