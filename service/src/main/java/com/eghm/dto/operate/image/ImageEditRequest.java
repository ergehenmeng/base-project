package com.eghm.dto.operate.image;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2018/11/29 17:00
 */
@Data
public class ImageEditRequest {

    @Schema(description = "id主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "图片名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "图片名称不能为空")
    private String title;

    @Schema(description = "图片类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "图片类型不能为空")
    private Integer imageType;

    @Schema(description = "备注信息")
    @Size(max = 100, message = "备注信息不能超过100字")
    private String remark;

}
