package com.eghm.vo.business.order.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/28
 */

@Data
public class FirstExpressVO {

    @ApiModelProperty("快递id")
    private Long id;

    @ApiModelProperty("快递内容(最新节点)")
    private String context;
}
