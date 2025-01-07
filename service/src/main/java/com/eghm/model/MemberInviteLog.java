package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("member_invite_log")
@EqualsAndHashCode(callSuper = true)
public class MemberInviteLog extends BaseEntity {

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "邀请人id")
    private Long inviteMemberId;

}