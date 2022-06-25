package com.eghm.model.dto.help;

import com.eghm.model.validation.annotation.OptionByte;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/20 20:23
 */
@Data
public class HelpAddRequest implements Serializable {

    private static final long serialVersionUID = -8577111153647154240L;

    @ApiModelProperty(value = "帮助说明分类", required = true)
    @NotNull(message = "请选择分类")
    private Byte classify;

    @ApiModelProperty(value = "是否显示 0:不显示 1:显示", required = true)
    @OptionByte(value = {0, 1}, message = "显示状态不合法")
    private Byte state;

    @ApiModelProperty(value = "问", required = true)
    @NotBlank(message = "\"问\"不能为空")
    private String ask;

    @ApiModelProperty(value = "答", required = true)
    @NotBlank(message = "\"答\"不能为空")
    private String answer;

}

