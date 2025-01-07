package com.eghm.vo.business.order.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.RefundState;
import com.eghm.vo.business.order.adjust.OrderAdjustResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "店铺名称")
    private String storeName;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @Schema(description = "订单状态")
    private OrderState state;

    @Schema(description = "当前订单所处的退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @Schema(description = "订单金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;

    @Schema(description = "运费")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer fee;

    @Schema(description = "优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer discountAmount;

    @Schema(description = "付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

    @Schema(description = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @Schema(description = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @Schema(description = "关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeTime;

    @Schema(description = "订单关闭类型 1:过期自动取消 2:用户取消 3:退款成功")
    private CloseType closeType;

    @Schema(description = "订单备注信息")
    private String remark;

    @Schema(description = "收货人姓名")
    private String nickName;

    @Schema(description = "收货人手机号")
    private String mobile;

    @Schema(description = "省份id")
    @JsonIgnore
    private Long provinceId;

    @Schema(description = "城市id")
    @JsonIgnore
    private Long cityId;

    @Schema(description = "县区id")
    @JsonIgnore
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "订单商品")
    private List<ItemOrderListVO> itemList;

    @Schema(description = "已发货商品信息")
    private List<ItemShippedResponse> shippedList;

    @Schema(description = "改价记录")
    private List<OrderAdjustResponse> adjustList;

    @Schema(description = "该用户其他未发货订单")
    private List<ItemUnShippedOrderResponse> unShippedList;
}
