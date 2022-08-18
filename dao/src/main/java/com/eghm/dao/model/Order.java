package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.common.enums.ref.ProductType;
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

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty(value = "单价")
    private Integer price;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "商品类型")
    private ProductType productType;

    @ApiModelProperty(value = "是否支持退款 0:不支持 1:支持")
    private Boolean supportRefund;

    @ApiModelProperty(value = "订单状态 0:待支付 1:支付处理中 2:支付成功,待使用 3:已使用,待评价 4:已完成 5:已关闭")
    private Integer state;

    @ApiModelProperty(value = "退款状态 1:退款申请中 2: 退款中 3: 退款拒绝 4: 退款成功")
    private Integer refundState;

    @ApiModelProperty(value = "关闭类型 1:过期自动关闭 2:用户取消 3: 退款完成")
    private Integer closeType;

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
}
