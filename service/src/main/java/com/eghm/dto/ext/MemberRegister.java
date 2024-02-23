package com.eghm.dto.ext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/8/19 15:52
 */
@Data
public class MemberRegister {

    @ApiModelProperty("微信openId(公众号)")
    private String mpOpenId;

    @ApiModelProperty("小程序openId(小程序)")
    private String maOpenId;

    @ApiModelProperty("unionId")
    private String unionId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("密码")
    private String pwd;

    @ApiModelProperty("注册渠道")
    private String channel;

    @ApiModelProperty("性别 0:未知 1:男 2:女")
    private Integer sex;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("注册ip")
    private String registerIp;
}
