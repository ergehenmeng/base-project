package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.WithdrawWay;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 商家信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@TableName("merchant")
@EqualsAndHashCode(callSuper = true)
public class Merchant extends BaseEntity {

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "企业类型: 1:个人 2:企业")
    private Integer enterpriseType;

    @ApiModelProperty(value = "社会统一信用代码")
    private String creditCode;

    @ApiModelProperty(value = "营业执照图片")
    private String businessLicenseUrl;

    @ApiModelProperty(value = "法人姓名")
    private String legalName;

    @ApiModelProperty(value = "法人身份证")
    private String legalIdCard;

    @ApiModelProperty(value = "法人身份证图片")
    private String legalUrl;

    @ApiModelProperty(value = "商家类型 1:景区 2:民宿 4:餐饮 8:零售 16:线路 32:场馆")
    private Integer type;

    @ApiModelProperty(value = "联系人电话")
    private String mobile;

    @ApiModelProperty(value = "账户名")
    private String account;

    @ApiModelProperty("关联的系统用户id")
    private Long userId;

    @ApiModelProperty(value = "平台服务费,单位:%")
    private BigDecimal platformServiceRate;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "提现方式 1:手动提现 2:自动提现")
    private WithdrawWay withdrawWay;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "微信openid")
    private String openId;

    @ApiModelProperty(value = "微信授权手机号")
    private String authMobile;
}
