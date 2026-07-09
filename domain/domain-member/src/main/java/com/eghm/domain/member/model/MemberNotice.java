package com.eghm.domain.member.model;

import com.eghm.domain.shared.model.BaseEntity;

import com.eghm.domain.shared.enums.MessageType;
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

    public void create(Long memberId, MessageType messageType, String title, String content) {
        this.memberId = memberId;
        this.messageType = messageType;
        this.title = title;
        this.content = content;
        this.isRead = false;
    }

    public void createFromLog(Long memberId, Long noticeLogId, MessageType messageType, String title, String content) {
        this.create(memberId, messageType, title, content);
        this.noticeLogId = noticeLogId;
    }

    public void markRead() {
        this.isRead = true;
    }

    public void markUnread() {
        this.isRead = false;
    }

    public boolean isRead() {
        return Boolean.TRUE.equals(this.isRead);
    }

}
