package com.eghm.vo.poi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/22
 */

@Data
public class PoiAreaResponse {

    @ApiModelProperty(value = "区域名称")
    private String title;

    @ApiModelProperty(value = "区域编号")
    private String code;
}
