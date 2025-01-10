package com.eghm.vo.business.order.restaurant;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.RefundState;
import com.eghm.enums.ref.RefundType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherOrderDetailVO {

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

    @Schema(description = "是否支持退款 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
    private OrderState state;

    @Schema(description = "当前订单所处的退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @Schema(description = "单价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer price;

    @Schema(description = "优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer discountAmount;

    @Schema(description = "总付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

    @Schema(description = "创建订单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @Schema(description = "联系人手机号")
    private String mobile;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    @Schema(description = "有效期购买之日起")
    private Integer validDays;

    @Schema(description = "生效时间(包含)")
    private LocalDate effectDate;

    @Schema(description = "失效日期(包含)")
    private LocalDate expireDate;

    @Schema(description = "使用开始时间")
    private String effectTime;

    @Schema(description = "使用截止时间")
    private String expireTime;

    @Schema(description = "核销码")
    private String verifyNo;

    @Schema(description = "兑换码(只支持线路/民宿/场馆/餐饮)")
    private String cdKey;

    @Schema(description = "兑换码优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer cdKeyAmount;

    @Schema(description = "订单备注信息")
    private String remark;

}
