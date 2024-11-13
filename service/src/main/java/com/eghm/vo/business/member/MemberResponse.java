package com.eghm.vo.business.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/7/19
 */
@Data
public class MemberResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("状态 0:注销 1正常")
    private Boolean state;

    @ApiModelProperty("总积分数")
    private Integer score;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("生日yyyyMMdd")
    private String birthday;

    @ApiModelProperty("性别 0:未知 1:男 2:女 ")
    private Integer sex;

    @ApiModelProperty("注册渠道 PC,ANDROID,IOS,H5,OTHER")
    private String channel;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

}
