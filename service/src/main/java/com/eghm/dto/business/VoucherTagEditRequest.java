package com.eghm.dto.business;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
* @author 二哥很猛
* @since 2024-10-09
*/
@Data
public class VoucherTagEditRequest {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键不能为空")
    private Long id;

    @Schema(description = "标签名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标签名称不能为空")
    @Size(max = 10, message = "标签名称最大10字符")
    private String title;

    @Schema(description = "餐饮商家id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择所属店铺")
    private Long restaurantId;

    @Schema(description = "状态 0:禁用 1:启用", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean state;

    @Schema(description = "备注")
    private String remark;
}
