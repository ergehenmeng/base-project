package com.eghm.dto.poi;

import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */

@Data
public class PoiAreaEditRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "区域名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 20, message = "区域名称最大20字符")
    @NotBlank(message = "区域名称不能为空")
    @WordChecker(message = "区域名称存在敏感词")
    private String title;

    @Schema(description = "区域编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 20, message = "区域编号最大20字符")
    @NotBlank(message = "区域编号不能为空")
    private String code;

    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @Schema(description = "维度", requiredMode = Schema.RequiredMode.REQUIRED)
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @Schema(description = "省份id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @Schema(description = "城市id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @Schema(description = "区县id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "区县不能为空")
    private Long countyId;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "详细地址最大100字符")
    @NotBlank(message = "详细地址不能为空")
    @WordChecker(message = "详细地址存在敏感词")
    private String detailAddress;

    @Schema(description = "备注信息")
    @WordChecker(message = "备注信息存在敏感词")
    private String remark;
}
