package com.eghm.vo.business.order.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.DeliveryState;
import com.eghm.enums.ref.ItemRefundState;
import com.eghm.vo.business.merchant.address.MerchantAddressVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/9
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemOrderRefundVO {

    @Schema(description = "订单id")
    private Long id;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "配送状态 0:初始 1:待发货 2:待收货 3:待自提 4:已收货")
    private DeliveryState deliveryState;

    @Schema(description = "退款状态 0:未退款 1:已退款")
    private ItemRefundState refundState;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "下单总数量")
    private Integer num;

    @Schema(description = "商品封面图(如果有sku图则优先显示sku图,否则显示商品图)")
    private String coverUrl;

    @Schema(description = "规格名称(多规格)")
    private String skuTitle;

    @Schema(description = "销售价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @Schema(description = "退款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer refundAmount;

    @Schema(description = "退款快递费")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer expressFeeAmount;

    @Schema(description = "退款积分")
    private Integer scoreAmount;

    @Schema(description = "店铺收货地址")
    private MerchantAddressVO storeAddress;
}
