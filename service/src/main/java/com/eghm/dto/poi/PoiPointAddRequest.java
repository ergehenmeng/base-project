package com.eghm.dto.poi;

import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */

@Data
public class PoiPointAddRequest {

    @Schema(description = "点位名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 20, message = "点位名称最大20字符")
    @NotBlank(message = "点位名称不能为空")
    @WordChecker(message = "点位名称存在敏感词")
    private String title;

    @Schema(description = "区域", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请选择区域")
    private String areaCode;

    @Schema(description = "封面图", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "封面图不能为空")
    private List<String> coverList;

    @Schema(description = "点位类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择点位类型")
    private Long typeId;

    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "纬度不能为空")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细地址不能为空")
    @Size(max = 50, message = "详细地址最大50字符")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @Schema(description = "详细介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;
}
