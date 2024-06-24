package com.eghm.vo.business.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/6/24
 */

@Data
public class MerchantDetailVO {

    @ApiModelProperty("id主键")
    private Long id;

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
    private List<Integer> typeList;

    @ApiModelProperty(value = "联系人电话")
    private String mobile;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;
}
