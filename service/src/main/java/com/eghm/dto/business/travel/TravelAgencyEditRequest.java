package com.eghm.dto.business.travel;

import com.eghm.validation.annotation.Phone;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 殿小二
 * @since 2023/2/18
 */

@Data
public class TravelAgencyEditRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "旅行社名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "旅行社名称不能为空")
    @Size(min = 2, max = 20, message = "旅行社名称长度2~20位")
    @WordChecker(message = "旅行社名称存在敏感词")
    private String title;

    @Schema(description = "店铺logo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "店铺logo不能能为空")
    private String logoUrl;

    @Schema(description = "旅行社电话")
    @Phone(message = "旅行社电话格式不正确")
    private String phone;

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
    @Size(max = 100, message = "详细地址长度1~100位")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "经度不能能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "维度不能为空")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @Schema(description = "旅行社描述信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "描述信息不能为空")
    @Size(max = 50, message = "描述信息最大50字符")
    @WordChecker(message = "描述信息存在敏感词")
    @Expose(serialize = false)
    private String depict;

    @Schema(description = "旅行社图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "旅行社图片不能为空")
    private List<String> coverList;

    @Schema(description = "详细介绍信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;
}
