package com.eghm.vo.business.order.restaurant;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 门票订单列表vo
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
public class RestaurantOrderResponse {

    @ApiModelProperty("图片")
    private String coverUrl;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("餐饮券名称")
    private String title;

    @ApiModelProperty("餐饮店名称")
    private String restaurantName;

    @ApiModelProperty("支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @ApiModelProperty("购买数量")
    private Integer num;

    @ApiModelProperty(value = "订单状态")
    private OrderState state;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @ApiModelProperty("关闭类型 1:过期自动关闭 2:用户取消 3: 退款完成")
    private CloseType closeType;

    @ApiModelProperty("订单关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeTime;

    @ApiModelProperty("总优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer discountAmount;

    @ApiModelProperty("总付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

    @ApiModelProperty("购买人昵称")
    private String nickName;

    @ApiModelProperty("购买人手机号")
    private String mobile;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
