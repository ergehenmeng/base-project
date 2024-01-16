package com.eghm.vo.business.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 商家信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-27
 */
@Data
public class MerchantDetailResponse {

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

    @ApiModelProperty(value = "商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路")
    private Integer type;

    @ApiModelProperty(value = "联系人电话")
    private String mobile;

    @ApiModelProperty(value = "微信授权手机号")
    private String authMobile;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

}
