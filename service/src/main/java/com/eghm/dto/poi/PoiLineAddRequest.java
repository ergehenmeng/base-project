package com.eghm.dto.poi;

import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/21
 */
@Data
public class PoiLineAddRequest {

    @Schema(description = "线路名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "线路名称不能为空")
    @Size(max = 20, message = "线路名称最大20字符")
    @WordChecker(message = "线路名称存在敏感词")
    private String title;

    @Schema(description = "所属区域编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请选择区域")
    private String areaCode;

    @Schema(description = "封面图", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "封面图不能为空")
    private List<String> coverList;

    @Schema(description = "预计游玩时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预计游玩时间不能为空")
    private BigDecimal playTime;

    @Schema(description = "详细介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细介绍不能为空")
    @WordChecker(message = "详细介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;
}
