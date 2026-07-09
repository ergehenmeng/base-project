package com.eghm.dto.sys.family;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2025/12/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FamilyEditRequest extends FamilyAddRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "id不能为空")
    private String id;

}
