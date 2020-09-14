package com.eghm.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class UserInviteLog implements Serializable {
    /**
     * 主键<br>
     * 表 : user_invite_log<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 用户id<br>
     * 表 : user_invite_log<br>
     * 对应字段 : user_id<br>
     */
    private Integer userId;

    /**
     * 被邀请人id<br>
     * 表 : user_invite_log<br>
     * 对应字段 : invite_user_id<br>
     */
    private Integer inviteUserId;

    /**
     * 添加时间<br>
     * 表 : user_invite_log<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    private static final long serialVersionUID = 1L;

}