package com.eghm.business.model;

import com.eghm.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberInviteLog extends BaseEntity {

    /** 用户id */
    private Long memberId;

    /** 邀请人id */
    private Long inviteMemberId;

}
