package com.eghm.vo.business.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/9
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberVO {

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "今日是否签到")
    private Boolean signed;

    @Schema(description = "站内信未读数量")
    private Long unRead;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "微信openId")
    private String openId;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "总积分数")
    private Integer score;

    @Schema(description = "邀请码")
    private String inviteCode;

    @Schema(description = "性别 性别 0:未知 1:男 2:女 ")
    private Integer sex;
}
