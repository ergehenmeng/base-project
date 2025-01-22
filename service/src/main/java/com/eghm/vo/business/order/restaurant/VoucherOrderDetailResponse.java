package com.eghm.vo.business.order.restaurant;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.CloseType;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.eghm.enums.RefundState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 门票订单列表vo
 *
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
public class VoucherOrderDetailResponse {

    @Schema(description = "图片")
    private String coverUrl;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "名称")
    private String title;

    @Schema(description = "餐饮商家名称")
    private String restaurantName;

    @Schema(description = "餐饮商家id")
    private Long restaurantId;

    @Schema(description = "支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @Schema(description = "购买数量")
    private Integer num;

    @Schema(description = "已使用数量")
    private Integer useNum;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
    private OrderState state;

    @Schema(description = "当前订单所处的退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @Schema(description = "单价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer price;

    @Schema(description = "总优惠金额(优惠券)")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer discountAmount;

    @Schema(description = "兑换码(只支持线路/民宿/场馆/餐饮)")
    private String cdKey;

    @Schema(description = "兑换码优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer cdKeyAmount;

    @Schema(description = "付款金额=单价*数量-总优惠金额-兑换码优惠")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

    @Schema(description = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "联系人手机号")
    private String mobile;

    @Schema(description = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @Schema(description = "有效期购买之日起")
    private Integer validDays;

    @Schema(description = "生效时间(包含)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectDate;

    @Schema(description = "失效日期(包含)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @Schema(description = "使用开始时间")
    private String effectTime;

    @Schema(description = "使用截止时间")
    private String expireTime;

    @Schema(description = "订单关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeTime;

    @Schema(description = "关闭类型 1:过期自动关闭 2:用户取消 3: 退款完成")
    private CloseType closeType;

    @Schema(description = "订单备注信息")
    private String remark;

}
