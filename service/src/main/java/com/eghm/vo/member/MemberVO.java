package com.eghm.vo.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/9
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberVO {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("今日是否签到")
    private Boolean signed;

    @ApiModelProperty("站内信未读数量")
    private Long unRead;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("微信openId")
    private String openId;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("总积分数")
    private Integer score;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("性别 性别 0:未知 1:男 2:女 ")
    private Integer sex;
}
