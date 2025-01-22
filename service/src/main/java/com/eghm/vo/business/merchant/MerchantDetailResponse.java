package com.eghm.vo.business.merchant;

import com.eghm.convertor.BigDecimalOmitSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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

    @Schema(description = "商户名称")
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
    private List<Integer> typeList;

    @Schema(description = "平台服务费,单位:%")
    @JsonSerialize(using = BigDecimalOmitSerializer.class)
    private BigDecimal platformServiceRate;

    @Schema(description = "联系人电话")
    private String mobile;

    @Schema(description = "微信授权手机号")
    private String authMobile;

    @Schema(description = "详细地址")
    private String detailAddress;

}
