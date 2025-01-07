package com.eghm.dto.business.merchant;

import com.eghm.validation.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author 殿小二
 * @since 2022/5/27
 */
@Data
public class MerchantAddRequest {

    @Schema(description = "商家名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商家名称不能为空")
    @Size(min = 2, max = 20, message = "商家名称长度2~20位")
    @WordChecker(message = "商家名称包含敏感词")
    private String merchantName;

    @Schema(description = "联系人电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @Mobile(message = "联系人电话格式错误")
    private String mobile;

    @Schema(description = "账户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "账户名不能为空")
    @Size(min = 6, max = 15, message = "账户名长度6~15位")
    private String account;

    @Schema(description = "商家类型 1:景区 2:民宿 4:餐饮 8:零售 16:线路 32:场馆(多选时数字相加)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择商家类型")
    @RangeInt(min = 1, max = 63, message = "商家类型错误")
    private Integer type;

    @Schema(description = "企业类型: 1:个人 2:企业", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2}, message = "企业类型不合法")
    private Integer enterpriseType;

    @Schema(description = "社会统一信用代码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "社会统一信用代码不能为空")
    private String creditCode;

    @Schema(description = "营业执照图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "营业执照不能为空")
    private String businessLicenseUrl;

    @Schema(description = "法人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "法人姓名不能为空")
    @Size(min = 2, max = 20, message = "法人姓名长度2~20位")
    private String legalName;

    @Schema(description = "法人身份证", requiredMode = Schema.RequiredMode.REQUIRED)
    @IdCard(message = "法人身份证格式不正确")
    private String legalIdCard;

    @Schema(description = "法人身份证图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "法人身份证不能为空")
    private String legalUrl;

    @Schema(description = "省份id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择省份")
    private Long provinceId;

    @Schema(description = "城市id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择城市")
    private Long cityId;

    @Schema(description = "县区id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择县区")
    private Long countyId;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

}
