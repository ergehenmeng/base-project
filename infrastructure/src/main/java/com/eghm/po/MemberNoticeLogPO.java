package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员站内信日志(仅仅记录日志, 方便后续排查问题)
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-07
 */
@Data
@TableName("member_notice_log")
@EqualsAndHashCode(callSuper = false)
public class MemberNoticeLogPO extends BaseEntityPO {

    /** 通知名称 */
    private String title;

    /** 通知内容 */
    private String content;

    /** 消息类型 */
    private MessageType messageType;

    /** 消息参数 */
    private String params;

    /** 发送人id */
    private Long operatorId;

}
