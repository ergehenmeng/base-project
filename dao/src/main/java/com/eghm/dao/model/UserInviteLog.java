package com.eghm.dao.model;

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
public class UserInviteLog extends BaseEntity {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("被邀请人id")
    private Long inviteUserId;

}