package com.eghm.dto.sys.help;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2018/11/20 20:23
 */
@Data
public class HelpAddRequest {

    @Schema(description = "帮助说明分类", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择分类")
    private Integer helpType;

    @Schema(description = "是否显示 0:不显示 1:显示", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {0, 1}, message = "显示状态不合法")
    private Integer state;

    @Schema(description = "问", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "\"问\"不能为空")
    @WordChecker(message = "\"问\"存在敏感词")
    private String ask;

    @Schema(description = "答", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "\"答\"不能为空")
    @WordChecker(message = "\"答\"存在敏感词")
    @Expose(serialize = false)
    private String answer;

}

