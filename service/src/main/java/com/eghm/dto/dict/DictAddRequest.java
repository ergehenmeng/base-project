package com.eghm.dto.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:40
 */
@Data
public class DictAddRequest {

    @ApiModelProperty(value = "数据字典名称", required = true)
    @NotBlank(message = "名称不能为空")
    private String title;

    @ApiModelProperty(value = "字典编码", required = true)
    @NotBlank(message = "字典编码不能为空")
    private String nid;

    @ApiModelProperty(value = "是否锁定 true:锁定 false:不锁定", required = true)
    @NotNull(message = "锁定状态不能为空")
    private Boolean locked;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
