package com.eghm.vo.business.order.item;

import com.eghm.convertor.SplitterJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class ItemUnShippedOrderResponse {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("商品封面图")
    @JsonSerialize(using = SplitterJsonSerializer.class)
    private String coverUrl;
}
