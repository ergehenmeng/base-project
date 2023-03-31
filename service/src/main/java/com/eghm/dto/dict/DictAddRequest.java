package com.eghm.dto.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:40
 */
@Data
public class DictAddRequest implements Serializable {

    private static final long serialVersionUID = -9001797427236045372L;

    @ApiModelProperty(value = "数据字典名称", required = true)
    @NotBlank(message = "名称不能为空")
    private String title;

    @ApiModelProperty(value = "数据字典隐藏值", required = true)
    @NotNull(message = "隐藏值不能为空")
    private Integer hiddenValue;

    @ApiModelProperty(value = "显示值", required = true)
    @NotBlank(message = "显示值不能为空")
    private String  showValue;

    @ApiModelProperty(value = "是否锁定 true:锁定 false:不锁定", required = true)
    @NotNull(message = "锁定状态不能为空")
    private Boolean locked;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
