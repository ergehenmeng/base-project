package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("member_invite_log")
@EqualsAndHashCode(callSuper = true)
public class MemberInviteLogPO extends BaseEntityPO {

    /** 用户id */
    private Long memberId;

    /** 邀请人id */
    private Long inviteMemberId;

}
