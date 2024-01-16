package com.eghm.dto.business.merchant;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
@Data
public class MerchantAddRequest {

    @ApiModelProperty(value = "商家名称", required = true)
    @NotBlank(message = "商家名称不能为空")
    @Size(min = 2, max = 20, message = "商家名称长度2~20位")
    @WordChecker(message = "商家名称包含敏感词")
    private String merchantName;

    @ApiModelProperty(value = "联系人电话", required = true)
    @NotBlank(message = "联系人电话不能为空")
    @Size(min = 7, max = 15, message = "联系人电话长度7~15位")
    private String mobile;

    @ApiModelProperty(value = "商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路, 多选时数字相加", required = true)
    @NotNull(message = "请选择商家类型")
    @RangeInt(min = 1, max = 31, message = "商家类型错误")
    private Integer type;

    @ApiModelProperty(value = "企业类型: 1:个人 2:企业")
    @OptionInt(value = {1, 2}, message = "企业类型不合法")
    private Integer enterpriseType;

    @ApiModelProperty(value = "社会统一信用代码")
    @NotBlank(message = "社会统一信用代码不能为空")
    private String creditCode;

    @ApiModelProperty(value = "营业执照图片")
    @NotBlank(message = "营业执照不能为空")
    private String businessLicenseUrl;

    @ApiModelProperty(value = "法人姓名")
    @NotBlank(message = "法人姓名不能为空")
    private String legalName;

    @ApiModelProperty(value = "法人身份证")
    @NotBlank(message = "法人身份证不能为空")
    private String legalIdCard;

    @ApiModelProperty(value = "法人身份证图片")
    @NotBlank(message = "法人身份证不能为空")
    private String legalUrl;

    @ApiModelProperty(value = "省份id")
    @NotNull(message = "请选择省份")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    @NotNull(message = "请选择城市")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    @NotNull(message = "请选择县区")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

}
