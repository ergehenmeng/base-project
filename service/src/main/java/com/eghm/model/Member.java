package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 用户信息表
 *
 * @author 二哥很猛
 */
@Data
@TableName("`member`")
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity {

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "微信小程序openId")
    private String maOpenId;

    @Schema(description = "微信公众号openId")
    private String mpOpenId;

    @Schema(description = "微信unionId")
    private String unionId;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "登陆密码")
    private String pwd;

    @Schema(description = "状态 0:冻结 1:正常")
    private Boolean state;

    @Schema(description = "总积分数")
    private Integer score;

    @Schema(description = "邀请码")
    private String inviteCode;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "身份证号码,前6位加密")
    private String idCard;

    @Schema(description = "生日yyyyMMdd")
    private String birthday;

    @Schema(description = "性别 性别 0:未知 1:男 2:女 ")
    private Integer sex;

    @Schema(description = "注册渠道 PC,ANDROID,IOS,H5,OTHER")
    private String channel;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "注册地址")
    private Long registerIp;

    @Schema(description = "注册日期")
    private LocalDate createDate;

    @Schema(description = "创建月份")
    private String createMonth;
}