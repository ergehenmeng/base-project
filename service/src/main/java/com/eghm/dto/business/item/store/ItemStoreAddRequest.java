package com.eghm.dto.business.item.store;

import com.eghm.validation.annotation.Phone;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/1
 */
@Data
public class ItemStoreAddRequest {

    @Schema(description = "店铺名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "店铺名称长度2~20位")
    @NotBlank(message = "店铺名称不能为空")
    @WordChecker(message = "店铺名称存在敏感词")
    private String title;

    @Schema(description = "店铺logo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "店铺logo不能为空")
    private String logoUrl;

    @Schema(description = "商家")
    @NotNull(message = "请选择所属商家")
    private Long merchantId;

    @Schema(description = "封面图", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "店铺封面图不能为空")
    private List<String> coverList;

    @Schema(description = "营业时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "营业时间不能为空")
    private String openTime;

    @Schema(description = "省id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @Schema(description = "城市id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @Schema(description = "县区id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "县区不能为空")
    private Long countyId;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细地址不能为空")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    @Schema(description = "商家电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @Phone(message = "商家电话格式错误")
    private String telephone;

    @Schema(description = "商家介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商家介绍不能为空")
    @WordChecker(message = "商家介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;

}
