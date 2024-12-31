package com.eghm.dto.operate.help;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2018/11/20 20:23
 */
@Data
public class HelpAddRequest {

    @ApiModelProperty(value = "帮助说明分类", required = true)
    @NotNull(message = "请选择分类")
    private Integer helpType;

    @ApiModelProperty(value = "是否显示 0:不显示 1:显示", required = true)
    @OptionInt(value = {0, 1}, message = "显示状态不合法")
    private Integer state;

    @ApiModelProperty(value = "问", required = true)
    @NotBlank(message = "\"问\"不能为空")
    @WordChecker(message = "\"问\"存在敏感词")
    private String ask;

    @ApiModelProperty(value = "答", required = true)
    @NotBlank(message = "\"答\"不能为空")
    @WordChecker(message = "\"答\"存在敏感词")
    @Expose(serialize = false)
    private String answer;

}

