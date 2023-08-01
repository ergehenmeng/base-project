package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order")
@ApiModel(value="Order对象", description="订单表")
public class Order extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "封面图片(第一张)")
    private String coverUrl;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("商品所属店铺")
    private Long storeId;

    @ApiModelProperty(value = "单价")
    private Integer price;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "商品类型")
    private ProductType productType;
    
    @ApiModelProperty("是否为多订单,普通商品且购物车购买才可能是多订单,即一个订单对应多商品")
    private Boolean multiple;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty("退款描述信息")
    private String refundDescribe;
    
    @ApiModelProperty("配送方式")
    private DeliveryType deliveryType;

    @ApiModelProperty("支付流水号")
    private String outTradeNo;

    @ApiModelProperty("支付方式")
    private PayType payType;

    @ApiModelProperty(value = "订单状态")
    private OrderState state;

    @ApiModelProperty("当前订单所处的退款状态 1:退款申请中 2: 退款中 3: 退款拒绝 4: 退款成功 5: 退款失败(该状态和退款中在C端用户看来都是退款中) 6: 线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @ApiModelProperty("关闭类型 1:过期自动关闭 2:用户取消 3: 退款完成")
    private CloseType closeType;

    @ApiModelProperty(value = "优惠金额")
    private Integer discountAmount;

    @ApiModelProperty(value = "付款金额=单价*数量-优惠金额")
    private Integer payAmount;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty("完成时间")
    private LocalDateTime completeTime;

    @ApiModelProperty("订单关闭时间")
    private LocalDateTime closeTime;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty("订单备注信息")
    private String remark;
}
