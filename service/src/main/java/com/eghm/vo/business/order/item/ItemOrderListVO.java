package com.eghm.vo.business.order.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.DeliveryState;
import com.eghm.enums.ref.ItemRefundState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/9
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemOrderListVO {

    @ApiModelProperty("订单id")
    private Long id;

    @ApiModelProperty("商品id")
    private Long itemId;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty("配送状态 0: 初始 1:待发货 2: 待收货 3:待自提 4:已收货")
    private DeliveryState deliveryState;

    @ApiModelProperty("退款状态 0:未退款 1:已退款")
    private ItemRefundState refundState;

    @ApiModelProperty("下单总数量")
    private Integer num;

    @ApiModelProperty(value = "商品封面图(如果有sku图则优先显示sku图,否则显示商品图)")
    private String coverUrl;

    @ApiModelProperty(value = "规格名称(多规格)")
    private String skuTitle;

    @ApiModelProperty(value = "销售价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

}
