package com.eghm.dto.business.travel;

import com.eghm.validation.annotation.Phone;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 殿小二
 * @since 2023/2/18
 */

@Data
public class TravelAgencyAddRequest {

    @ApiModelProperty(value = "旅行社名称", required = true)
    @NotBlank(message = "旅行社名称不能为空")
    @Size(min = 2, max = 20, message = "旅行社名称长度2~20位")
    @WordChecker(message = "旅行社名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "店铺logo", required = true)
    @NotBlank(message = "店铺logo不能能为空")
    private String logoUrl;

    @ApiModelProperty("商家")
    @NotNull(message = "请选择所属商家")
    private Long merchantId;

    @ApiModelProperty("旅行社电话")
    @Phone(message = "旅行社电话格式不正确")
    private String phone;

    @ApiModelProperty(value = "省份id", required = true)
    @NotNull(message = "请选择省份")
    private Long provinceId;

    @ApiModelProperty(value = "城市id", required = true)
    @NotNull(message = "请选择城市")
    private Long cityId;

    @ApiModelProperty(value = "县区id", required = true)
    @NotNull(message = "请选择县区")
    private Long countyId;

    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "详细地址不能为空")
    @Size(max = 100, message = "详细地址长度1~100位")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @ApiModelProperty(value = "经度", required = true)
    @NotNull(message = "经度不能能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", required = true)
    @NotNull(message = "维度不能为空")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @ApiModelProperty(value = "旅行社描述信息", required = true)
    @NotBlank(message = "描述信息不能为空")
    @Size(max = 50, message = "描述信息最大50字符")
    @WordChecker(message = "描述信息存在敏感词")
    private String depict;

    @ApiModelProperty(value = "旅行社图片", required = true)
    @NotEmpty(message = "旅行社图片不能为空")
    private List<String> coverList;

    @ApiModelProperty(value = "详细介绍信息", required = true)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;
}
