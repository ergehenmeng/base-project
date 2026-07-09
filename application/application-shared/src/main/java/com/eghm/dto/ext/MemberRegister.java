package com.eghm.dto.ext;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/8/19 15:52
 */
@Data
public class MemberRegister {

    @Schema(description = "微信openId(公众号)")
    private String mpOpenId;

    @Schema(description = "小程序openId(小程序)")
    private String maOpenId;

    @Schema(description = "unionId")
    private String unionId;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "密码")
    @Expose(serialize = false)
    private String pwd;

    @Schema(description = "注册渠道")
    private String channel;

    @Schema(description = "性别 0:未知 1:男 2:女")
    private Integer sex;

    @Schema(description = "邀请码")
    private String inviteCode;

    @Schema(description = "注册ip")
    private String registerIp;
}
