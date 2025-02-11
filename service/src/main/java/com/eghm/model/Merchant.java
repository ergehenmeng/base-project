package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.WithdrawWay;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "商家名称")
    private String merchantName;

    @Schema(description = "企业类型: 1:个人 2:企业")
    private Integer enterpriseType;

    @Schema(description = "社会统一信用代码")
    private String creditCode;

    @Schema(description = "营业执照图片")
    private String businessLicenseUrl;

    @Schema(description = "法人姓名")
    private String legalName;

    @Schema(description = "法人身份证")
    private String legalIdCard;

    @Schema(description = "法人身份证图片")
    private String legalUrl;

    @Schema(description = "商家类型 1:景区 2:民宿 4:餐饮 8:零售 16:线路 32:场馆")
    private Integer type;

    @Schema(description = "联系人电话")
    private String mobile;

    @Schema(description = "账户名")
    private String account;

    @Schema(description = "关联的系统用户id")
    private Long userId;

    @Schema(description = "平台服务费,单位:%")
    private BigDecimal platformServiceRate;

    @Schema(description = "省份id")
    private Long provinceId;

    @Schema(description = "提现方式 1:手动提现 2:自动提现")
    private WithdrawWay withdrawWay;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "县区id")
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "微信openid")
    private String openId;

    @Schema(description = "微信授权手机号")
    private String authMobile;
}
