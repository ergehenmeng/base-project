package com.eghm.dto.sys.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2019/1/14 11:40
 */
@Data
public class DictAddRequest {

    @Schema(description = "数据字典名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "名称不能为空")
    @Size(min = 2, max = 10, message = "字典名称长度2~10位")
    private String title;

    @Schema(description = "字典编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "字典编码不能为空")
    private String nid;

    @Schema(description = "字典分类: 1: 系统字典 2: 业务字典")
    @NotNull(message = "字典分类不能为空")
    private Integer dictType;

    @Schema(description = "备注信息")
    @Size(max = 100, message = "备注信息不能超过100字")
    private String remark;
}
