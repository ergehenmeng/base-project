package com.eghm.dto.sys.family;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2025/12/16
 */
@Data
public class FamilyEditRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "id不能为空")
    private String id;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "姓名不能为空")
    @Size(min = 1, max = 10, message = "姓名最大10字符")
    private String name;

    @Schema(description = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Schema(description = "父辈", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请选择父辈")
    private String pid;

    @Schema(description = "状态 false:未绝户 true:已绝户", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择状态")
    private Boolean state;

    @Schema(description = "简介信息")
    @Size(max = 500, message = "备注信息不能超过500字")
    private String remark;
}
