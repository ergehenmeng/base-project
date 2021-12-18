package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class UserInviteLog extends BaseEntity {

    /**
     * 用户id<br>
     * 表 : user_invite_log<br>
     * 对应字段 : user_id<br>
     */
    private Long userId;

    /**
     * 被邀请人id<br>
     * 表 : user_invite_log<br>
     * 对应字段 : invite_user_id<br>
     */
    private Long inviteUserId;

}