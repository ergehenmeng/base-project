package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("member_invite_log")
public class MemberInviteLog extends BaseEntity {

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("邀请人id")
    private Long inviteMemberId;

}