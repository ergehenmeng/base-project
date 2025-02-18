package com.eghm.dto.business.item.express;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/22
 */
@Data
public class ExpressTemplateEditRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 20, message = "快递模板名称长度2~20位")
    @NotBlank(message = "快递模板名称不能为空")
    private String title;

    @Schema(description = "状态 0:禁用 1:启用")
    @NotNull(message = "状态不能为空")
    private Integer state;

    @Schema(description = "计费方式 1:按件数 2:按重量", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2}, message = "计费方式不合法")
    private Integer chargeMode;

    @Schema(description = "配送区域", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "配送区域不能为空")
    @Size(min = 1, max = 20, message = "配送区域不能超过20个")
    private List<ExpressTemplateRegionRequest> regionList;

    @Assign
    @Schema(hidden = true, description = "登录人商户ID")
    private Long merchantId;
}
