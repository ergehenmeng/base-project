package com.eghm.model.dto.config;


import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/1/18 16:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ConfigQueryRequest extends PagingQuery implements Serializable {

    private static final long serialVersionUID = -2384592001035426711L;

    @ApiModelProperty("系统参数类型")
    private Integer classify;

    @ApiModelProperty("是否禁止编辑 true:禁止编辑 false:可以编辑")
    private Boolean locked;

}
