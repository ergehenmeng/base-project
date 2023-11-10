package com.eghm.vo.business.order.item;

import com.eghm.enums.ref.ItemRefundState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/9
 */

@Data
public class ItemVO {

    @ApiModelProperty("商品id")
    private Long itemId;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty("退款状态")
    private ItemRefundState refundState;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty(value = "商品封面图(如果有sku图则优先显示sku图,否则显示商品图)")
    private String coverUrl;

    @ApiModelProperty(value = "规格名称")
    private String skuTitle;

    @ApiModelProperty(value = "销售价格")
    private Integer salePrice;

}
