package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.domain.shared.enums.MemberState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户信息表 PO.
 */
@Data
@TableName("`member`")
@EqualsAndHashCode(callSuper = false)
public class MemberPO {

    /** id主键 */
    private Long id;

    /** 昵称 */
    private String nickName;

    /** 手机号码 */
    private String mobile;

    /** 账号 */
    private String account;

    /** 微信小程序openId */
    private String maOpenId;

    /** 微信公众号openId */
    private String mpOpenId;

    /** 微信unionId */
    private String unionId;

    /** 电子邮箱 */
    private String email;

    /** 登陆密码 */
    private String pwd;

    /** 状态 0:冻结 1:正常 */
    private MemberState state;

    /** 总积分数 */
    private Integer score;

    /** 邀请码 */
    private String inviteCode;

    /** 真实姓名 */
    private String realName;

    /** 身份证号码,前6位加密 */
    private String idCard;

    /** 生日yyyyMMdd */
    private String birthday;

    /** 性别 性别 0:未知 1:男 2:女  */
    private Integer sex;

    /** 注册渠道 PC,ANDROID,IOS,H5,OTHER */
    private String channel;

    /** 头像 */
    private String avatar;

    /** 注册地址 */
    private Long registerIp;

    /** 注册日期 */
    private LocalDate createDate;

    /** 创建月份 */
    private String createMonth;

    /** 添加时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 是否已删除 0:未删除 1:已删除 */
    private Boolean deleted;
}
