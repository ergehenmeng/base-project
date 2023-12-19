package com.eghm.vo.business.order.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.RefundState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemOrderDetailResponse {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("店铺id")
    private String storeId;

    @ApiModelProperty("支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @ApiModelProperty(value = "订单状态")
    private OrderState state;

    @ApiModelProperty("当前订单所处的退款状态 1:退款申请中 2: 退款中 3: 退款拒绝 4: 退款成功 5: 退款失败(该状态和退款中在C端用户看来都是退款中) 6: 线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @ApiModelProperty(value = "优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer discountAmount;

    @ApiModelProperty("总付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

    @ApiModelProperty("创建订单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @ApiModelProperty("关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeTime;

    @ApiModelProperty("订单关闭类型 1:过期自动取消 2:用户取消 3:")
    private CloseType closeType;

    @ApiModelProperty("订单备注信息")
    private String remark;

    @ApiModelProperty("收货人姓名")
    private String nickName;

    @ApiModelProperty("收货人手机号")
    private String mobile;

    @ApiModelProperty(value = "省份id")
    @JsonIgnore
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    @JsonIgnore
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    @JsonIgnore
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty("订单商品")
    private List<ItemOrderListVO> itemList;

    @ApiModelProperty("已发货商品信息")
    private List<ItemShippedResponse> shippedList;
}
