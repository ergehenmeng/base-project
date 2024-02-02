package com.eghm.vo.business.venue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenueSiteVO {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "场地名称")
    private String title;

}
