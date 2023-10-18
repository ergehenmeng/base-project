package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("member_invite_log")
@EqualsAndHashCode(callSuper = true)
public class MemberInviteLog extends BaseEntity {

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("邀请人id")
    private Long inviteMemberId;

}