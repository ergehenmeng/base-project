package com.eghm.dto.help;

import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 20:30
 */
@Data
public class HelpEditRequest implements Serializable {

    private static final long serialVersionUID = 3415746590445570716L;

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "帮助说明分类", required = true)
    @NotNull(message = "请选择分类")
    private Integer classify;

    @ApiModelProperty(value = "是否显示 0:不显示 1:显示", required = true)
    @OptionInt(value = {0, 1}, message = "显示状态不合法")
    private Integer state;

    @ApiModelProperty(value = "问", required = true)
    @NotBlank(message = "\"问\"不能为空")
    private String ask;

    @ApiModelProperty(value = "答", required = true)
    @NotBlank(message = "\"答\"不能为空")
    private String answer;

}
