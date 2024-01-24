package com.eghm.vo.business.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/24
 */

@Data
public class GroupOrderVO {

    @ApiModelProperty("拼团id")
    private Long bookingId;

    @ApiModelProperty("已拼团数量")
    private Integer num;
}
