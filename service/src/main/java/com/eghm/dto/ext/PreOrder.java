package com.eghm.dto.ext;

import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 下单前,订单信息
 *
 * @author 二哥很猛
 * @since 2022/8/21
 */
@Data
public class PreOrder {

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("商品销售价格")
    private Integer salePrice;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("商品退款方式")
    private RefundType refundType;

    @ApiModelProperty("配送方式 0:无需发货 1:快递包邮 2:自提")
    private DeliveryType deliveryType;

    @ApiModelProperty("退款描述信息")
    private String refundDescribe;

    @ApiModelProperty("商品类型 ticket:门票 homestay:民宿 restaurant:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @ApiModelProperty("是否支持使用优惠券")
    private Boolean supportedCoupon;

    @ApiModelProperty("是否为热销商品")
    private Boolean hotSell;

    @ApiModelProperty("是否为多订单")
    private Boolean multiple;
}
