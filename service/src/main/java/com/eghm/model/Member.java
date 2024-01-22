package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 用户信息表
 *
 * @author 二哥很猛
 */
@Data
@TableName("member")
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity {

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("微信小程序openId")
    private String maOpenId;

    @ApiModelProperty("微信公众号openId")
    private String mpOpenId;

    @ApiModelProperty("微信unionId")
    private String unionId;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("登陆密码")
    private String pwd;

    @ApiModelProperty("状态 0:冻结 1正常")
    private Boolean state;

    @ApiModelProperty("总积分数")
    private Integer score;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("身份证号码,前6位加密")
    private String idCard;

    @ApiModelProperty("生日yyyyMMdd")
    private String birthday;

    @ApiModelProperty("性别 性别 0:未知 1:男 2:女 ")
    private Integer sex;

    @ApiModelProperty("注册渠道 PC,ANDROID,IOS,H5,OTHER")
    private String channel;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("注册地址")
    private Long registerIp;

    @ApiModelProperty("注册日期")
    private LocalDate createDate;
}