package com.eghm.vo.business.order.item;

import com.eghm.enums.ref.DeliveryState;
import com.eghm.enums.ref.ItemRefundState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/9
 */

@Data
public class ItemOrderListVO {

    @ApiModelProperty("商品id")
    private Long itemId;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty("配送状态 0: 初始 1:待发货 2: 待收货 3:已收货")
    private DeliveryState deliveryState;

    @ApiModelProperty("退款状态")
    private ItemRefundState refundState;

    @ApiModelProperty("下单总数量")
    private Integer num;

    @ApiModelProperty("已退款数量")
    private Integer refundNum;

    @ApiModelProperty(value = "商品封面图(如果有sku图则优先显示sku图,否则显示商品图)")
    private String coverUrl;

    @ApiModelProperty(value = "规格名称")
    private String skuTitle;

    @ApiModelProperty(value = "销售价格")
    private Integer salePrice;

}
