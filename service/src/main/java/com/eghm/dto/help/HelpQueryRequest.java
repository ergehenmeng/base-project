package com.eghm.dto.help;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2018/11/20 20:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HelpQueryRequest extends PagingQuery {

    @ApiModelProperty("帮助说明类型")
    private Integer classify;

    @ApiModelProperty("是否显示 0:不显示 1:显示")
    private Integer state;

}
