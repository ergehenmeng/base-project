package com.eghm.dto.config;


import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2018/1/18 16:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ConfigQueryRequest extends PagingQuery {

    @ApiModelProperty("是否禁止编辑 true:禁止编辑 false:可以编辑")
    private Boolean locked;

}
