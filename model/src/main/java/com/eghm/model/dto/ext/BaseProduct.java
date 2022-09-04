package com.eghm.model.dto.ext;

import com.eghm.common.enums.ref.DeliveryType;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.enums.ref.RefundType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品基本信息
 * @author 二哥很猛
 * @date 2022/8/21
 */
@Data
public class BaseProduct {

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("商品销售价格")
    private Integer salePrice;

    @ApiModelProperty("商品退款方式")
    private RefundType refundType;
    
    @ApiModelProperty("配送方式")
    private DeliveryType deliveryType;

    @ApiModelProperty("退款描述信息")
    private String refundDescribe;

    @ApiModelProperty("商品类型")
    private ProductType productType;

    @ApiModelProperty("是否支持使用优惠券")
    private Boolean supportedCoupon;

    @ApiModelProperty("是否为热销商品")
    private Boolean hotSell;
    
    @ApiModelProperty("是否为多订单")
    private Boolean multiple;
}
