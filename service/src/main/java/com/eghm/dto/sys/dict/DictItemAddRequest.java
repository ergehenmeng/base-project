package com.eghm.dto.sys.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/1/14 11:40
 */
@Data
public class DictItemAddRequest {

    @Schema(description = "字典编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "字典编码不能为空")
    private String nid;

    @Schema(description = "数据字典隐藏值", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "隐藏值不能为空")
    private Integer hiddenValue;

    @Schema(description = "显示值", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "显示值不能为空")
    @Size(min = 1, max = 20, message = "显示值长度1~20位")
    private String showValue;
}
