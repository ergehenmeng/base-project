package com.eghm.vo.business.item.express;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/25
 */
@Data
public class ExpressSelectResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "模板名称")
    private String title;

    @ApiModelProperty(value = "计费方式 1:按件数 2:按重量")
    private Integer chargeMode;

    @ApiModelProperty(value = "状态 0:禁用 1:启用")
    private Integer state;
}
